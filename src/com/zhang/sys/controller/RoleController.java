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

import com.zhang.sys.domain.Role;
import com.zhang.sys.service.RoleService;

import net.sf.json.JSONObject;

/**
 * 角色管理-Controller
 * @author Brian.Zhang
 * Jun 17, 2016-10:03:29 AM
 */
@RequestMapping("/role")
@Controller
public class RoleController {
	
	@Autowired
	private RoleService roleService;

	/**
	 * 角色信息主界面
	 * 
	 * @author zhanggd
	 * @return
	 * @throws  
	 * Jun 17, 2016-10:06:43 AM
	 */
	@RequestMapping("/list")
	public String list() {
		return "sys/role";
	}
	
	/**
	 * 查询角色信息
	 * 
	 * @author zhanggd
	 * @param page
	 * @param rows
	 * @return
	 * @throws  
	 * Jun 17, 2016-10:07:36 AM
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public Map<String, Object> dataGrid(String page,String rows) {
		//当前页  
        int intPage = Integer.parseInt((page == null || page == "0") ? "1":page);  
        //每页显示条数  
        int number = Integer.parseInt((rows == null || rows == "0") ? "10":rows);  
        //每页的开始记录  第一页为1  第二页为number +1   
        int start = (intPage-1)*number;  
        
        List<Role> list = roleService.listAllRoles(start, number);
        int listCount = roleService.listAllRolesCount();
        
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", list);
		map.put("total", listCount);
		return map;
	}
	
	/**
	 * 增加角色信息
	 * 
	 * @author zhanggd
	 * @param response
	 * @param role
	 * @throws  
	 * Jun 17, 2016-10:26:54 AM
	 */
	@RequestMapping("/addRole")
	public void addRole(HttpServletResponse response, Role role) {
		roleService.addRole(role);
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
	 * 编辑角色信息
	 * 
	 * @author zhanggd
	 * @param response
	 * @param role
	 * @param id
	 * @throws  
	 * Jun 17, 2016-10:37:49 AM
	 */
	@RequestMapping("/editRole")
	public void editRole(HttpServletResponse response, Role role,  @RequestParam("id") int id) {
		role.setId(id);
		roleService.editRole(role);
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
	 * 删除角色信息
	 * 
	 * @author zhanggd
	 * @param response
	 * @param id
	 * @throws  
	 * Jun 17, 2016-10:39:23 AM
	 */
	@RequestMapping("/deleteRole")
	public void deleteRole(HttpServletResponse response, int id) {
		roleService.deleteRoleById(id);
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
	 * 保存用户和角色的关系
	 * 
	 * @author zhanggd
	 * @param response
	 * @param userId
	 * @throws  
	 * Jul 26, 2016-4:36:24 PM
	 */
	@RequestMapping("/saveRolesByUserId")
	public void saveRolesByUserId(HttpServletResponse response, @RequestParam("userId") String userId, String roleIds) {
		roleService.saveRolesByUserId(userId, roleIds);
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
	 * 根据用户ID获取角色树列表
	 * 
	 * @author zhanggd
	 * @return
	 * @throws  
	 * Jun 17, 2016-2:25:02 PM
	 */
	@RequestMapping("roleTreeByUserId")
	@ResponseBody
	public List<Map<String, Object>> roleTreeByUserId(@RequestParam("userId") String userId) {
		List<Map<String, Object>> list = roleService.roleTreeByUserId(userId);
		return list;  
	}
}