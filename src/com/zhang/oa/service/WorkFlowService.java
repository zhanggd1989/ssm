package com.zhang.oa.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhang.common.util.SessionHelper;
import com.zhang.oa.domain.LeaveBill;
import com.zhang.oa.domain.WorkFlowDefination;
import com.zhang.sys.domain.User;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class WorkFlowService {

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;
	
	@Autowired
	private LeaveBillService leaveBillService;
	
	@Autowired
	private HistoryService historyService;

	/**
	 * 查询流程定义
	 * 
	 * @author zhanggd
	 * @param start
	 * @param number
	 * @return
	 * @throws  
	 * Jul 28, 2016-1:52:01 PM
	 */
	public List<Map<String, Object>> findProcessDefinitionList(int start, int number) {
		List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()// 创建一个流程定义查询（表：act_re_procdef）
				.orderByProcessDefinitionVersion().asc()// 按照版本的升序排列
				.listPage(start, number);// 分页查询
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		if (list != null && list.size() > 0) {
			for (ProcessDefinition pd : list) {
				map = new HashMap<String, Object>();
				map.put("id", pd.getId());
				map.put("name", pd.getName());
				map.put("key", pd.getKey());
				map.put("version", pd.getVersion());
				map.put("resourceName", pd.getResourceName());
				map.put("diagramResourceName", pd.getDiagramResourceName());
				map.put("deploymentId", pd.getDeploymentId());
			}
			returnList.add(map);
		}
		return returnList;
	}

	/**
	 * 查询流程定义条数
	 * 
	 * @author zhanggd
	 * @return
	 * @throws  
	 * Jul 28, 2016-1:49:44 PM
	 */
	public long findProcessDefinitionListCount() {
		long count = repositoryService.createProcessDefinitionQuery()// 创建一个流程定义查询（表：act_re_procdef）
				.orderByProcessDefinitionVersion().asc()// 按照版本的升序排列
				.count();
		return  count;
	}
	
	/**
	 * 查看流程图
	 * 
	 * @author zhanggd
	 * @param deploymentId
	 * @throws 
	 * Jul 5, 2016-10:02:27 AM
	 */
	public InputStream viewImage(String deploymentId) {
		// 1.获取图片资源名称
		List<String> list = repositoryService.getDeploymentResourceNames(deploymentId);//（表：act_ge_bytearray）
		// 2.定义图片资源的名称
		String resourceName = "";
		if (list != null && list.size() > 0) {
			for (String name : list) {
				if (name.indexOf(".png") >= 0) {
					resourceName = name;
				}
			}
		}
		// 3.获取图片的输入流
		return repositoryService.getResourceAsStream(deploymentId, resourceName);//（表：act_ge_bytearray）

	}
	
	/**
	 * 部署流程定义（从zip）
	 * 
	 * @author zhanggd
	 * @param workFlowDefination
	 * @throws 
	 * Jul 4, 2016-4:28:29 PM
	 */
	public void deploymentProcessDefinition_zip(WorkFlowDefination workFlowDefination) {
		try {
			ZipInputStream zipInputStream = new ZipInputStream(workFlowDefination.getSourceFile().getInputStream());
			repositoryService.createDeployment()// 创建一个部署对象
					.name(workFlowDefination.getName())// 添加部署的名称
					.addZipInputStream(zipInputStream)// 指定zip格式的文件完成部署
					.deploy();// 完成部署
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询流程部署
	 * 
	 * @author zhanggd
	 * @param start
	 * @param number
	 * @return
	 * @throws 
	 * Jul 5, 2016-10:36:22 AM
	 */
	public List<Map<String, Object>> findDeploymentList(int start, int number) {
		List<Deployment> list = repositoryService.createDeploymentQuery()// 创建一个部署对象查询（表：act_re_deployment）
				.orderByDeploymenTime().asc()//// 按照部署时间的升序排列
				.listPage(start, number);// 分页查询
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		if (list != null && list.size() > 0) {
			for (Deployment dl : list) {
				map = new HashMap<String, Object>();
				map.put("id", dl.getId());
				map.put("name", dl.getName());
				map.put("deploymentTime", dl.getDeploymentTime());
			}
			returnList.add(map);
		}
		return returnList;
	}

	/**
	 * 查询流程部署条数
	 * 
	 * @author zhanggd
	 * @return
	 * @throws  
	 * Aug 1, 2016-2:20:57 PM
	 */
	public long findDeploymentListCount() {
		long count = repositoryService.createDeploymentQuery()// 创建一个部署对象查询（表：act_re_deployment）
				.orderByDeploymenTime().asc()//// 按照部署时间的升序排列
				.count();
		return  count;
	}
	
	/**
	 * 删除流程部署
	 * 
	 * @author zhanggd
	 * @param id
	 * @throws 
	 * Jul 5, 2016-11:03:24 AM
	 */
	public void deleteUserByDeploymentId(String id) {
		// 级联删除，不管流程是否启动，都可以删除
		repositoryService.deleteDeployment(id, true);
	}

	/**
	 * 启动流程实例
	 * 
	 * @author zhanggd
	 * @param leaveBillId
	 * @param approval
	 * @throws 
	 * Jul 5, 2016-3:25:14 PM
	 */
	public void startProcess(String key, String objectId, Map<String, Object> variables) {
		// 使用流程定义的key，启动流程实例，同时设置流程变量，同时向正在执行的执行对象表中的字段BUSINESS_KEY添加业务数据，同时让流程关联业务
		runtimeService.startProcessInstanceByKey(key, objectId, variables);// (表：act_ru_task,act_ru_variable)
	}

	/**
	 * 查询待办任务
	 * 
	 * @author zhanggd
	 * @param start
	 * @param number
	 * @return
	 * @throws 
	 * Jul 6, 2016-3:48:01 PM
	 */
	public List<Map<String, Object>> findTaskListByUserId(int start, int number) {
		// 指定任务办理人
		User user = (User)SessionHelper.getSession().getAttribute("userInfo");
		String assignee = String.valueOf(user.getId());
		
		List<Task> list = taskService.createTaskQuery()// 创建一个任务查询对象
				.taskAssignee(assignee)// 指定个人任务查询，指定办理人
				.orderByTaskCreateTime().asc()// 按照任务创建时间的升序排列
				.listPage(start, number);
		
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		if (list != null && list.size() > 0) {
			for (Task task : list) {
				map = new HashMap<String, Object>();
				map.put("id", task.getId());
				map.put("name", task.getName());
				map.put("createTime", task.getCreateTime());
				map.put("assigneeId", user.getId());
				map.put("assignee", user.getRealName());
			}
			returnList.add(map);
		}
		return returnList;
	}
	
	/**
	 * 查询待办任务条数
	 * 
	 * @author zhanggd
	 * @return
	 * @throws  
	 * Jul 29, 2016-10:50:46 AM
	 */
	public long findTaskListCountByUserId() {
		// 指定任务办理人
		User user = (User)SessionHelper.getSession().getAttribute("userInfo");
		String assignee = String.valueOf(user.getId());
		
		long count = taskService.createTaskQuery()// 创建一个任务查询对象
				.taskAssignee(assignee)// 指定个人任务查询，指定办理人
				.orderByTaskCreateTime().asc()// 按照任务创建时间的升序排列
				.count();
		return count;
	}

	/**
	 * 根据任务ID获取流程定义对象
	 * 
	 * @author zhanggd
	 * @param taskId
	 * @return
	 * @throws 
	 * Jul 7, 2016-4:50:37 PM
	 */
	public ProcessDefinition findProcessDefinitionByTaskId(String taskId) {
		// 根据任务ID获取任务对象
		Task task = taskService.createTaskQuery()// 创建任务查询对象，对应表act_ru_task
				.taskId(taskId)// 使用任务ID查询
				.singleResult();
		// 获取流程定义ID
		String processDefinitionId = task.getProcessDefinitionId();
		// 根据流程定义ID获取流程定义对象
		ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()// 创建流程定义查询对象，对应表act_re_procdef
				.processDefinitionId(processDefinitionId)// 使用流程定义ID查询
				.singleResult();
		return pd;
	}
	
	/**
	 * 查看当前流程活动，获取当前流程活动对应的坐标x,y,width,height，将4个值存放到Map<String,Object>中
	 * 
	 * @author zhanggd
	 * @param taskId
	 * @return
	 * @throws 
	 * Jul 7, 2016-5:04:49 PM
	 */
	public Map<String, Object> findCoordingByTaskId(String taskId) {
		// 存放当前活动坐标信息
		Map<String, Object> map = new HashMap<String, Object>();
		/**
		 * 1.使用任务ID，查询任务对象
		 */
		Task task = taskService.createTaskQuery()// 创建任务查询对象，对应表act_ru_task
				.taskId(taskId)// 使用任务ID查询
				.singleResult();
		/**
		 * 2.使用流程定义的ID，查询流程定义的实体对象（对应.bpmn文件中的数据
		 */
		String processDefinitionId = task.getProcessDefinitionId();
		ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService
				.getProcessDefinition(processDefinitionId);
		/**
		 * 3.使用流程实例ID，查询当前活动对应的流程实例对象
		 */
		String processInstanceId = task.getProcessInstanceId();
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()// 创建流程实例查询对象，对应表act_ru_execution
				.processInstanceId(processInstanceId)// 使用流程实例ID查询
				.singleResult();
		/**
		 * 4.使用当前活动ID,查询当前活动对象
		 */
		String activityId = pi.getActivityId();
		ActivityImpl activityImpl = processDefinitionEntity.findActivity(activityId);
		/**
		 * 5.获取坐标
		 */
		map.put("x", activityImpl.getX());
		map.put("y", activityImpl.getY());
		map.put("width", activityImpl.getWidth());
		map.put("height", activityImpl.getHeight());
		return map;
	}
	
	/**
	 * 根据任务ID获取ProcessDefinitionEntiy对象，从而获取当前任务完成之后的连线名称，并放置到List<String>集合中
	 * 
	 * @author zhanggd
	 * @param taskId
	 * @return
	 * @throws 
	 * Jul 8, 2016-11:26:12 AM
	 */
	public List<String> findOutComeListByTaskId(String taskId) {
		// 存放连线的名称集合信息
		List<String> list = new ArrayList<String>();
		/**
		 * 1.使用任务ID，查询任务对象
		 */
		Task task = taskService.createTaskQuery()// 创建任务查询对象，对应表act_ru_task
				.taskId(taskId)// 使用任务ID查询
				.singleResult();
		/**
		 * 2.使用流程定义的ID，查询流程定义的实体对象（对应.bpmn文件中的数据
		 */
		String processDefinitionId = task.getProcessDefinitionId();
		ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService
				.getProcessDefinition(processDefinitionId);
		/**
		 * 3.使用流程实例ID，查询当前活动对应的流程实例对象
		 */
		String processInstanceId = task.getProcessInstanceId();
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
				.processInstanceId(processInstanceId)// 使用流程实例ID查询
				.singleResult();
		
		/**
		 * 4.使用当前活动ID,查询当前活动对象
		 */
		String activityId = pi.getActivityId();
		ActivityImpl activityImpl = processDefinitionEntity.findActivity(activityId);
		/**
		 * 5.获取连线名称
		 */
		List<PvmTransition> pvmList = activityImpl.getOutgoingTransitions();
		if (pvmList != null && pvmList.size() > 0) {
			for (PvmTransition pvm : pvmList) {
				String name = (String) pvm.getProperty("name");
				if (StringUtils.isNotBlank(name)) {
					list.add(name);
				} else {
					list.add("提交");
				}
			}
		}
		return list;
	}
	
	/**
	 * 提交任务
	 * 
	 * @author zhanggd
	 * @param leaveBill
	 * @throws 
	 * Jul 11, 2016-1:44:00 PM
	 */
	public void saveSubmitTask(LeaveBill leaveBill) {
		// 获取任务ID
		String taskId = leaveBill.getTaskId();
		// 获取任务审批人
		String approval = leaveBill.getApproval();
		// 获取连线的名称
		String outcome = leaveBill.getOutcome();
		// 批注信息
		String message = leaveBill.getComment();
		/**
		 * 1：添加批注信息
		 */
		// 获取任务对象
		Task task = taskService.createTaskQuery()// 创建任务查询对象，对应表act_ru_task
				.taskId(taskId)// 使用任务ID查询
				.singleResult();
		// 获取流程实例ID
		String processInstanceId = task.getProcessInstanceId();
		/**
		 * 注意：添加批注的时候，由于Activiti底层代码是使用： 
		 * String userId = Authentication.getAuthenticatedUserId(); 
		 * CommentEntity comment = new CommentEntity(); 
		 * comment.setUserId(userId);
		 * 所有需要从Session中获取当前登录人，作为该任务的办理人（审核人），对应act_hi_comment表中的User_ID的字段，
		 * 所以要求，添加配置执行使用Authentication.setAuthenticatedUserId();添加当前任务的审核人
		 */
		User user = (User)SessionHelper.getSession().getAttribute("userInfo");
		String userId = String.valueOf(user.getId());
		Authentication.setAuthenticatedUserId(userId);
		taskService.addComment(taskId, processInstanceId, message);// 对应表act_hi_comment
		/**
		 * 2：完成任务
		 */
		Map<String, Object> variables = new HashMap<String, Object>();
		/**
		 * **************当前节点获取下一节点及下一节点变量**************************
		 */
		// 1.根据流程定义的ID，查询流程定义的实体对象
		String processDefinitionId = task.getProcessDefinitionId();
		ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService
				.getProcessDefinition(processDefinitionId);
		// 2.根据流程定义的实体对象，获取所有的活动
		List<ActivityImpl> activitiList = processDefinitionEntity.getActivities();
		// 3.根据执行对象ID,获取执行的实体对象
		String excId = task.getExecutionId();
		ExecutionEntity execution = (ExecutionEntity) runtimeService.createExecutionQuery().executionId(excId)
				.singleResult();
		// 4.根据执行的实体对象，获取当前活动ID
		String activitiId = execution.getActivityId();
		// 5.遍历所有的活动，查询出当前任务和下一步任务的相关信息
		for (ActivityImpl activityImpl : activitiList) {
			String id = activityImpl.getId();// 节点ID
			if (activitiId.equals(id)) {
				System.out.println("当前任务：" + activityImpl.getId() + ":" + activityImpl.getProperty("name"));// 获取当前节点
				List<PvmTransition> outTransitions = activityImpl.getOutgoingTransitions();// 获取当前节点的所有输出线路
				for (PvmTransition tr : outTransitions) {
					PvmActivity ac = tr.getDestination(); // 获取线路的终点节点
					String name = (String)tr.getProperty("name");// 线路名称
					System.out.println("线路名称：" + name);
					if (outcome.equals(name)) {
						System.out.println("下一步任务：" + ac.getId() + ":" + ac.getProperty("name"));
						if ("userTask".equals(ac.getProperty("type"))) {
							ActivityImpl activityImpl1 = processDefinitionEntity.findActivity(ac.getId());
							TaskDefinition taskDefinition = ((UserTaskActivityBehavior) activityImpl1.getActivityBehavior())
									.getTaskDefinition();
							String applyString = taskDefinition.getAssigneeExpression().getExpressionText();
							String apply = applyString.substring(2, applyString.length()-1);
							System.out.println(apply);
							variables.put("outcome", outcome);
							variables.put(apply, approval);
						}
					}
				}
				break;
			}
		}
		taskService.complete(taskId, variables);
		/**
		 * 3：判断流程状态
		 */
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
				.processInstanceId(processInstanceId)// 使用流程实例ID查询
				.singleResult();
		if (pi == null) {// 流程结束
			// 更新请假单表的状态从1变成2（审核中-->审核完成）
			leaveBill.setState(2);
			leaveBillService.updateState(leaveBill);
		}
	}
	
	/**
	 * 根据任务ID查询任务的审核信息
	 * 
	 * @author zhanggd
	 * @param taskId
	 * @return
	 * @throws 
	 * Jul 7, 2016-3:31:55 PM
	 */
	public List<Comment> findHisCommentByTaskId(String taskId) {
		List<Comment> historyComments = new ArrayList<Comment>();
		// 使用当前的任务ID，查询当前流程对应的历史任务ID
		// 使用当前任务ID，获取当前任务实体
		HistoricTaskInstance task = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();// 创建任务查询对象，对应表act_ru_task
		// 获取流程实例
		String processInstanceId = task.getProcessInstanceId();
		HistoricProcessInstance hpi = historyService.createHistoricProcessInstanceQuery()
				.processInstanceId(processInstanceId)
				.singleResult();
		// 通过流程实例查询所有的（用户类型任务）的历史活动
		List<HistoricActivityInstance> hais = historyService.createHistoricActivityInstanceQuery()
				.processInstanceId(hpi.getId())
				.activityType("userTask")
				.list();
		// 查询每个历史任务的批注
		for (HistoricActivityInstance hai : hais) {
			String histroyTaskId = hai.getTaskId();
			List<Comment> comments = taskService.getTaskComments(histroyTaskId);
			// 如果当前任务有批注信息，添加到集合中
			if (comments != null && comments.size()>0) {
				historyComments.addAll(comments);
			}
		}
		// 返回
		return historyComments;
	}

	/**
	 * 查询已办任务
	 * 
	 * @author zhanggd
	 * @param start
	 * @param number
	 * @return
	 * @throws  
	 * Aug 1, 2016-2:20:35 PM
	 */
	public List<Map<String, Object>> findTaskDoneListByUserId(int start, int number) {
		// 指定任务办理人
		User user = (User)SessionHelper.getSession().getAttribute("userInfo");
		String assignee = String.valueOf(user.getId());
		
		List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery()// 创建一个历史任务查询对象
			.taskAssignee(assignee)// 指定任务班里人
			.orderByTaskCreateTime().asc()// 按照任务创建时间的升序排列
			.listPage(start, number);
				
		
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		if (list != null && list.size() > 0) {
			for (HistoricTaskInstance hti : list) {
				map = new HashMap<String, Object>();
				map.put("id", hti.getId());
				map.put("name", hti.getName());
				map.put("createTime", hti.getCreateTime());
				map.put("assigneeId", user.getId());
				map.put("assignee", user.getRealName());
			}
			returnList.add(map);
		}
		return returnList;
	}

	/**
	 * 查询已办任务条数
	 * 
	 * @author zhanggd
	 * @return
	 * @throws  
	 * Aug 1, 2016-2:35:26 PM
	 */
	public long findTaskDoneListCountByUserId() {
		// 指定任务办理人
		User user = (User)SessionHelper.getSession().getAttribute("userInfo");
		String assignee = String.valueOf(user.getId());
		
		long count = historyService.createHistoricTaskInstanceQuery()// 创建一个历史任务查询对象
				.taskAssignee(assignee)// 指定任务班里人
				.orderByTaskCreateTime().asc()// 按照任务创建时间的升序排列
				.count();
		return count;
	}
	
}