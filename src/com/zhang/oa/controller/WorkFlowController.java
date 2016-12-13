package com.zhang.oa.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhang.oa.domain.LeaveBill;
import com.zhang.oa.domain.WorkFlowDefination;
import com.zhang.oa.service.WorkFlowService;

import net.sf.json.JSONObject;

/**
 * 流程管理-Controller
 * @author Brian.Zhang
 * Jul 1, 2016-2:56:38 PM
 */
@RequestMapping("/workFlow")
@Controller
public class WorkFlowController {
	
	@Autowired
	private WorkFlowService workFlowService;
	
	/**
	 * 流程定义主界面
	 * 
	 * @author zhanggd
	 * @return
	 * @throws  
	 * Jul 1, 2016-3:01:07 PM
	 */
	@RequestMapping("/defination")
	public String defination() {
		return "oa/workFlowDefination";
	}
	
	/**
	 * 查询流程定义
	 * 
	 * @author zhanggd
	 * @throws  
	 * Jul 4, 2016-4:45:15 PM
	 */
	@RequestMapping("/definationGrid")
	@ResponseBody
	public Map<String, Object> definationGrid(String page,String rows) {
		//当前页  
        int intPage = Integer.parseInt((page == null || page == "0") ? "1":page);  
        //每页显示条数  
        int number = Integer.parseInt((rows == null || rows == "0") ? "10":rows);  
        //每页的开始记录  第一页为1  第二页为number + 1   
        int start = (intPage-1)*number;  
        
        List<Map<String, Object>> list = workFlowService.findProcessDefinitionList(start, number);
        long listCount = workFlowService.findProcessDefinitionListCount();
        
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", list);
		map.put("total", listCount);
		return map;
	}
	
	/**
	 * 查看流程图
	 * 
	 * @author zhanggd
	 * @return
	 * @throws Exception
	 * @throws  
	 * Jul 5, 2016-9:53:37 AM
	 */
	@RequestMapping("/viewImage")
	public void viewImage(HttpServletResponse response, @RequestParam("deploymentId") String deploymentId) throws Exception{
		InputStream in = workFlowService.viewImage(deploymentId);
		//4.从response对象获取输出流
		OutputStream out = response.getOutputStream();
		//5.将输入流中的数据读取出来，写到输出流中
		for(int b=-1;(b=in.read())!=-1;){
			out.write(b);
		}
		out.close();
		in.close();
	}
	
	/**
	 * 部署流程定义（从zip）
	 * 
	 * @author zhanggd
	 * @param workFlowDefination
	 * @throws  
	 * Jul 4, 2016-3:56:54 PM
	 */
	@RequestMapping("/upload")
	public void upload(HttpServletResponse response, WorkFlowDefination workFlowDefination) {
		workFlowService.deploymentProcessDefinition_zip(workFlowDefination);
		JSONObject object = new JSONObject();
		object.put("errorMsg", "");
		object.put("success", true);
		try {
			PrintWriter out = response.getWriter();
			out.write(object.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 流程部署主界面
	 * 
	 * @author zhanggd
	 * @return
	 * @throws  
	 * Jul 1, 2016-3:01:23 PM
	 */
	@RequestMapping("/deployment")
	public String deployment() {
		return "oa/workFlowDeployment";
	}
	
	/**
	 * 查询流程部署
	 * 
	 * @author zhanggd
	 * @param page
	 * @param rows
	 * @return
	 * @throws  
	 * Jul 5, 2016-10:34:50 AM
	 */
	@RequestMapping("/deploymentGrid")
	@ResponseBody
	public Map<String, Object> deploymentGrid(String page,String rows) {
		//当前页  
        int intPage = Integer.parseInt((page == null || page == "0") ? "1":page);  
        //每页显示条数  
        int number = Integer.parseInt((rows == null || rows == "0") ? "10":rows);  
        //每页的开始记录  第一页为1  第二页为number + 1   
        int start = (intPage-1)*number;  
        
        List<Map<String, Object>> list = workFlowService.findDeploymentList(start, number);
        long listCount = workFlowService.findDeploymentListCount();
        
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", list);
		map.put("total", listCount);
		return map;
	}
	
	/**
	 * 删除流程部署
	 * 
	 * @author zhanggd
	 * @param response
	 * @param id
	 * @throws  
	 * Jul 5, 2016-11:02:30 AM
	 */
	@RequestMapping("/deleteDeployment")
	public void deleteDeployment(HttpServletResponse response, String id) {
		workFlowService.deleteUserByDeploymentId(id);
		JSONObject object = new JSONObject();
		object.put("errorMsg", "");
		object.put("success", true);
		try {
			PrintWriter out = response.getWriter();
			out.write(object.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 待办任务主界面
	 * 
	 * @author zhanggd
	 * @return
	 * @throws  
	 * Jun 29, 2016-2:56:51 PM
	 */
	@RequestMapping("/taskTodo")
	public String taskTodo() {
		return "oa/taskTodo";
	}
	
	/**
	 * 查询待办任务
	 * 
	 * @author zhanggd
	 * @param page
	 * @param rows
	 * @return
	 * @throws  
	 * Jul 6, 2016-3:38:44 PM
	 */
	@RequestMapping("/taskTodoGrid")
	@ResponseBody
	public Map<String, Object> taskTodoGrid(String page,String rows) {
		//当前页  
        int intPage = Integer.parseInt((page == null || page == "0") ? "1":page);  
        //每页显示条数  
        int number = Integer.parseInt((rows == null || rows == "0") ? "10":rows);  
        //每页的开始记录  第一页为1  第二页为number + 1   
        int start = (intPage-1)*number;  
        
        List<Map<String, Object>> list = workFlowService.findTaskListByUserId(start, number);
        long listCount = workFlowService.findTaskListCountByUserId();
        
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", list);
		map.put("total", listCount);
		return map;
	} 
	
	/**
	 * 查看当前流程图
	 * 
	 * @author zhanggd
	 * @return
	 * @throws Exception
	 * @throws  
	 * Jul 5, 2016-9:53:37 AM
	 */
	@RequestMapping("/viewCurrentImage")
	public String viewCurrentImage(HttpServletRequest request, HttpServletResponse response, String taskId) throws Exception{
		/**一：查看流程图*/
		//1：根据任务ID获取任务对象，根据任务对象获取流程定义ID，根据流程定义ID获取流程定义对象
		ProcessDefinition pd = workFlowService.findProcessDefinitionByTaskId(taskId);
		request.setAttribute("deploymentId", pd.getDeploymentId());
		request.setAttribute("imageName", pd.getDiagramResourceName());
		/**二：查看当前流程活动，获取当前流程活动对应的坐标x,y,width,height，将4个值存放到Map<String,Object>中*/
		Map<String, Object> map = workFlowService.findCoordingByTaskId(taskId);
		request.setAttribute("map", map);
		return "oa/currentImage";
	}
	
	/**
	 * 提交任务
	 * 
	 * @author zhanggd
	 * @param response
	 * @throws  
	 * Jul 11, 2016-10:31:08 AM
	 */
	@RequestMapping("/submitTask")
	public void submitTask(HttpServletResponse response, LeaveBill leaveBill) {
		workFlowService.saveSubmitTask(leaveBill);
		JSONObject object = new JSONObject();
		object.put("errorMsg", "");
		object.put("success", true);
		try {
			PrintWriter out = response.getWriter();
			out.write(object.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据任务ID查询任务的历史审核信息
	 * 
	 * @author zhanggd
	 * @param page
	 * @param rows
	 * @return
	 * @throws  
	 * Jul 7, 2016-3:30:55 PM
	 */
	@RequestMapping("/viewHisCommentByTaskId")
	@ResponseBody
	public Map<String, Object> viewHisCommentByTaskId(@RequestParam("taskId") String taskId) {
		List<Comment> list = workFlowService.findHisCommentByTaskId(taskId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", list);
		return map;
	}
	
	/**
	 * 已办任务主界面
	 * @author zhanggd
	 * @return
	 * @throws  
	 * Jun 29, 2016-2:56:51 PM
	 */
	@RequestMapping("/taskDone")
	public String doneManage() {
		return "oa/taskDone";
	}
	
	/**
	 * 查询已办任务
	 * 
	 * @author zhanggd
	 * @param page
	 * @param rows
	 * @return
	 * @throws  
	 * Jul 6, 2016-3:38:44 PM
	 */
	@RequestMapping("/taskDoneGrid")
	@ResponseBody
	public Map<String, Object> taskDoneGrid(String page,String rows) {
		//当前页  
        int intPage = Integer.parseInt((page == null || page == "0") ? "1":page);  
        //每页显示条数  
        int number = Integer.parseInt((rows == null || rows == "0") ? "10":rows);  
        //每页的开始记录  第一页为1  第二页为number + 1   
        int start = (intPage-1)*number;  
        
        List<Map<String, Object>> list = workFlowService.findTaskDoneListByUserId(start, number);
        long listCount = workFlowService.findTaskDoneListCountByUserId();
        
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", list);
		map.put("total", listCount);
		return map;
	}
	
}