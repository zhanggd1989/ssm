package com.zhang.oa.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhang.common.util.SessionHelper;
import com.zhang.oa.dao.LeaveBillMapper;
import com.zhang.oa.domain.LeaveBill;
import com.zhang.sys.domain.User;

/**
 * 请假单管理-Service
 * @author Brian.Zhang
 * Jun 29, 2016-3:12:16 PM
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class LeaveBillService {
	
	@Autowired
	private LeaveBillMapper leaveBillMapper;
	
	@Autowired
	private WorkFlowService workFlowService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private HistoryService historyService;

	/**
	 * 获取所有LeaveBill对象
	 * 
	 * @author zhanggd
	 * @param start
	 * @param number
	 * @return
	 * @throws  
	 * Jun 29, 2016-3:15:20 PM
	 */
	@Transactional(readOnly = true)
	public List<LeaveBill> listAllLeaveBills(int start, int number) {
		return leaveBillMapper.listAllLeaveBills(start, number);
	}

	/**
	 * 获取所有LeaveBill对象条数
	 * 
	 * @author zhanggd
	 * @return
	 * @throws  
	 * Jun 29, 2016-3:16:39 PM
	 */
	@Transactional(readOnly = true)
	public int listAllLeaveBillsCount() {
		return leaveBillMapper.listAllLeaveBillsCount();
	}

	/**
	 * 增加LeaveBill对象
	 * 
	 * @author zhanggd
	 * @param leaveBill
	 * @throws  
	 * Jun 29, 2016-3:17:17 PM
	 */
	public void addLeaveBill(LeaveBill leaveBill) {
		leaveBillMapper.addLeaveBill(leaveBill);
	}

	/**
	 * 编辑LeaveBill对象
	 * 
	 * @author zhanggd
	 * @param leaveBill
	 * @throws  
	 * Jun 29, 2016-3:17:17 PM
	 */
	public void editLeaveBill(LeaveBill leaveBill) {
		leaveBillMapper.editLeaveBill(leaveBill);
	}

	/**
	 * 删除LeaveBill对象
	 * 
	 * @author zhanggd
	 * @param leaveBill
	 * @throws  
	 * Jun 29, 2016-3:17:17 PM
	 */
	public void deleteLeaveBillById(int id) {
		leaveBillMapper.deleteLeaveBillById(id);
	}

	/**
	 * 根据请假单ID获取LeaveBill对象
	 * 
	 * @author zhanggd
	 * @param leaveBillId
	 * @return
	 * @throws  
	 * Jul 5, 2016-3:28:28 PM
	 */
	@Transactional(readOnly = true)
	public LeaveBill findLeaveBillById(String leaveBillId) {
		return leaveBillMapper.findLeaveBillById(leaveBillId);
	}

	/**
	 * 更新请假单状态
	 * 
	 * @author zhanggd
	 * @param leaveBill
	 * @throws  
	 * Jul 28, 2016-4:19:11 PM
	 */
	public void updateState(LeaveBill leaveBill) {
		leaveBillMapper.updateState(leaveBill);
	}
	
	/**
	 * 申请请假
	 * 
	 * @author zhanggd
	 * @param leaveBillId
	 * @throws  
	 * Jul 28, 2016-4:23:11 PM
	 */
	public void startLeaveBill(String leaveBillId) {
		// 1：获取请假单ID，使用请假单ID，查询请假单的对象LeaveBill
		LeaveBill leaveBill = findLeaveBillById(leaveBillId);
		// 2：更新请假单的请假状态从0变成1（初始录入-->开始审批）
		leaveBill.setState(1);
		updateState(leaveBill);
		// 3：使用当前对象获取到流程定义的key（对象的名称就是流程定义的key）
		String key = leaveBill.getClass().getSimpleName();
		// 4：使用流程变量设置下一个任务的办理人
		Map<String, Object> variables = new HashMap<String, Object>();
		User user = (User) SessionHelper.getSession().getAttribute("userInfo");
		variables.put("inputUser", user.getId());
		/**
		 * 5： (1)使用流程变量设置字符串（格式：LeaveBill.id的形式），通过设置，让启动的流程（流程实例）关联业务
		 *    (2)使用正在执行对象表中的一个字段BUSINESS_KEY（Activiti提供的一个字段），让启动的流程（流程实例）关联业务
		 */
		// 格式：LeaveBill.id的形式（使用流程变量）
		String objectId = key + "." + leaveBillId;
		variables.put("objectId", objectId);
		workFlowService.startProcess(key, objectId, variables);
	}

	/**
	 * 根据任务ID获取LeaveBill对象
	 * 
	 * @author zhanggd
	 * @param taskId
	 * @return
	 * @throws  
	 * Jul 29, 2016-4:16:12 PM
	 */
	@Transactional(readOnly = true)
	public LeaveBill findLeaveBillByTaskId(String taskId) {
		/**
		 * ***********************************
		 * 一：使用任务ID，查找请假单ID，从而获取请假单信息*
		 * ***********************************
		 */
		/**
		 * 1.使用任务ID，查询任务对象
		 */
		Task task = taskService.createTaskQuery()// 创建任务查询对象，对应表act_ru_task
				.taskId(taskId)// 使用任务ID查询
				.singleResult();
		/**
		 * 2.使用流程实例ID，查询当前活动对应的流程实例对象
		 */
		String processInstanceId = task.getProcessInstanceId();
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
				.processInstanceId(processInstanceId)// 使用流程实例ID查询
				.singleResult();
		/**
		 * 3：使用流程实例对象,查询BUSINESS_KEY
		 */
		String buniness_key = pi.getBusinessKey();
		/**
		 * 4.BUSINESS_KEY，获取LeaveBillId
		 * 注：BUSINESS_KEY组成格式： key + "." + leaveBillId
		 */
		String leaveBillId = "";
		if (StringUtils.isNotBlank(buniness_key)) {
			// 截取字符串，取buniness_key小数点的第2个值
			leaveBillId = buniness_key.split("\\.")[1];
		}
		// 根据请假单ID获取LeaveBill对象
		LeaveBill leaveBill = findLeaveBillById(leaveBillId);
		/**
		 * *************************************************************************************
		 * 二：使用任务ID，查询ProcessDefinitionEntiy对象，从而获取当前任务完成之后的连线名称，并放置到List<String>集合中
		 * *************************************************************************************
		 * */
		List<String> outcomeList = workFlowService.findOutComeListByTaskId(taskId);
		SessionHelper.getSession().setAttribute("outcomeList", outcomeList);
		return leaveBill;
	}
	
	/**
	 * 根据请假单ID查看历史批准信息
	 * 
	 * @author zhanggd
	 * @param id
	 * @return
	 * @throws  
	 * Jul 12, 2016-1:41:06 PM
	 */
	public List<Comment> findHisCommentByLeaveBillId(String leaveBillId) {
		//使用请假单ID，查询请假单对象
		LeaveBill leaveBill = findLeaveBillById(leaveBillId);
		//获取对象的名称
		String objectName = leaveBill.getClass().getSimpleName();
		//组织流程表中的字段中的值
		String objectId = objectName+"."+leaveBillId;
		
		/**1:使用历史的流程实例查询，返回历史的流程实例对象，获取流程实例ID*/
//	    HistoricProcessInstance hpi = historyService.createHistoricProcessInstanceQuery()//对应历史的流程实例表
//						.processInstanceBusinessKey(objId)//使用BusinessKey字段查询
//						.singleResult();
//		//流程实例ID
//		String processInstanceId = hpi.getId();
		/**2:使用历史的流程变量查询，返回历史的流程变量的对象，获取流程实例ID*/
		HistoricVariableInstance hvi = historyService.createHistoricVariableInstanceQuery()//对应历史的流程变量表
						.variableValueEquals("objectId", objectId)//使用流程变量的名称和流程变量的值查询
						.singleResult();
		//流程实例ID
		String processInstanceId = hvi.getProcessInstanceId();
		List<Comment> list = taskService.getProcessInstanceComments(processInstanceId);
		return list;
	}

}