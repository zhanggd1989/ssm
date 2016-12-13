package com.zhang.sys.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhang.sys.domain.Resource;
import com.zhang.sys.service.ResourceService;

import net.sf.json.JSONObject;

/**
 * 资源管理-Controller
 * @author Brian.Zhang
 * Jun 24, 2016-10:15:52 AM
 */
@RequestMapping("/resource")
@Controller
public class ResouceController {
	
	@Autowired
	private ResourceService resourceService;

	/**
	 * 资源信息主界面
	 * 
	 * @author zhanggd
	 * @return
	 * @throws  
	 * Jun 20, 2016-2:38:58 PM
	 */
	@RequestMapping("/list")
	public String list() {
		return "sys/resource";
	}
	
	/**
	 * 查询资源信息
	 * 
	 * @author zhanggd
	 * @param page
	 * @param rows
	 * @return
	 * @throws  
	 * Jun 20, 2016-2:39:42 PM
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public Map<String, Object> dataGrid() {
        List<Map<String, Object>> list = resourceService.listAllResources();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", list);
		return map;
	}
	
	/**
	 * 增加资源信息
	 * 
	 * @author zhanggd
	 * @param request
	 * @param response
	 * @throws  
	 * Jun 24, 2016-11:09:12 AM
	 */
	@RequestMapping("/addResource")
	public void addResource(HttpServletResponse response, Resource resource) {
		resourceService.addResource(resource);
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
	 * 编辑资源信息
	 * 
	 * @author zhanggd
	 * @param response
	 * @param resource
	 * @param id
	 * @throws  
	 * Jun 24, 2016-11:13:22 AM
	 */
	@RequestMapping("/editResource")
	public void editResource(HttpServletResponse response, Resource resource,  @RequestParam("id") int id) {
		resource.setId(id);;
		resourceService.editResource(resource);
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
	 * 删除资源信息
	 * 
	 * @author zhanggd
	 * @param response
	 * @param id
	 * @throws  
	 * Jun 24, 2016-11:13:35 AM
	 */
	@RequestMapping("/deleteResource")
	public void deleteResource(HttpServletResponse response, int id) {
		resourceService.deleteResourceById(id);
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
	 * 保存角色和资源的关系
	 * 
	 * @author zhanggd
	 * @param response
	 * @param roleId
	 * @param resourceIds
	 * @throws  
	 * Jul 27, 2016-9:06:28 AM
	 */
	@RequestMapping("/saveResourcesByRoleId")
	public void saveResourcesByRoleId(HttpServletResponse response, @RequestParam("roleId") String roleId, String resourceIds) {
		resourceService.saveResourcesByRoleId(roleId, resourceIds);
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
	 * 根据角色ID获取资源树列表
	 * 
	 * @author zhanggd
	 * @return
	 * @throws  
	 * Jun 17, 2016-2:25:02 PM
	 */
	@RequestMapping("resourceTreeByRoleId")
	@ResponseBody
	public List<Map<String, Object>> resourceTreeByRoleId(@RequestParam(value="roleId") String roleId) {
		List<Map<String, Object>> list = resourceService.resourceTreeByRoleId(roleId);
		return list;  
	}
}