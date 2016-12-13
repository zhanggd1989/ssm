package com.zhang.oa.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.task.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhang.oa.domain.LeaveBill;
import com.zhang.oa.service.LeaveBillService;

import net.sf.json.JSONObject;

/**
 * 请假单管理-Controller
 * @author Brian.Zhang
 * Jun 29, 2016-1:45:48 PM
 */
@RequestMapping("/leaveBill")
@Controller
public class LeaveBillController {
	
	@Autowired
	private LeaveBillService leaveBillService;
	
	/**
	 * 请假单信息主界面
	 * 
	 * @author zhanggd
	 * @return
	 * @throws  
	 * Jun 29, 2016-2:56:51 PM
	 */
	@RequestMapping("/list")
	public String list() {
		return "oa/leaveBill";
	}
	
	/**
	 * 查询请假单信息
	 * 
	 * @author zhanggd
	 * @param page
	 * @param rows
	 * @return
	 * @throws  
	 * Jun 29, 2016-3:03:02 PM
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	 public Map<String, Object> dataGrid(String page, String rows) {
		//当前页  
        int intPage = Integer.parseInt((page == null || page == "0") ? "1":page);  
        //每页显示条数  
        int number = Integer.parseInt((rows == null || rows == "0") ? "10":rows);  
        //每页的开始记录  第一页为1  第二页为number +1   
        int start = (intPage-1)*number; 
        
        List<LeaveBill> list = leaveBillService.listAllLeaveBills(start, number);
        int listCount = leaveBillService.listAllLeaveBillsCount();
        Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", list);
		map.put("total", listCount);
		return map;
	 }
	
	/**
	 * 增加请假单信息
	 * 
	 * @author zhanggd
	 * @param response
	 * @param role
	 * @throws  
	 * Jun 17, 2016-10:26:54 AM
	 */
	@RequestMapping("/addLeaveBill")
	public void addLeaveBill(HttpServletResponse response, LeaveBill leaveBill) {
		leaveBillService.addLeaveBill(leaveBill);
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
	 * 编辑请假单信息
	 * 
	 * @author zhanggd
	 * @param response
	 * @param role
	 * @param id
	 * @throws  
	 * Jun 17, 2016-10:37:49 AM
	 */
	@RequestMapping("/editLeaveBill")
	public void editLeaveBill(HttpServletResponse response, LeaveBill leaveBill,  @RequestParam("id") int id) {
		leaveBill.setId(id);
		leaveBillService.editLeaveBill(leaveBill);
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
	 * 删除请假单信息
	 * 
	 * @author zhanggd
	 * @param response
	 * @param id
	 * @throws  
	 * Jun 17, 2016-10:39:23 AM
	 */
	@RequestMapping("/deleteLeaveBill")
	public void deleteLeaveBill(HttpServletResponse response, int id) {
		leaveBillService.deleteLeaveBillById(id);
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
	 * 申请请假
	 * 
	 * @author zhanggd
	 * @param response
	 * @return
	 * @throws  
	 * Jul 5, 2016-3:09:53 PM
	 */
	@RequestMapping("/startLeaveBill")
	public void startLeaveBill(HttpServletResponse response, @RequestParam("leaveBillId") String leaveBillId){
		leaveBillService.startLeaveBill(leaveBillId);
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
	 * 根据任务ID获取LeaveBill对象
	 * 
	 * @author zhanggd
	 * @param taskId
	 * @return
	 * @throws  
	 * Jul 7, 2016-2:48:17 PM
	 */
	@RequestMapping("/viewLeaveBillByTaskId")
	@ResponseBody
	public LeaveBill viewLeaveBillByTaskId(HttpServletRequest request, @RequestParam("taskId") String taskId) {
		return leaveBillService.findLeaveBillByTaskId(taskId);
	}
	
	/**
	 * 根据请假单ID查询任务的历史审核信息
	 * 
	 * @author zhanggd
	 * @return
	 * @throws  
	 * Jul 12, 2016-1:37:06 PM
	 */
	@RequestMapping("/viewHisCommentByLeaveBillId")
	@ResponseBody
	public Map<String, Object> viewHisCommentByLeaveBillId(@RequestParam("leaveBillId") String leaveBillId){
		List<Comment> commentList = leaveBillService.findHisCommentByLeaveBillId(leaveBillId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", commentList);
		return map;
	}
}