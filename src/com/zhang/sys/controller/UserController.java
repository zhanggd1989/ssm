package com.zhang.sys.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhang.sys.domain.User;
import com.zhang.sys.service.UserService;

import net.sf.json.JSONObject;

/**
 * 用户管理-Controller
 * @author Brian.Zhang
 * Jun 15, 2016-4:04:10 PM
 */
@RequestMapping("/user")
@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	/**
	 * 用户信息主界面
	 * 
	 * @author zhanggd
	 * @return
	 * @throws  
	 * Jun 17, 2016-10:06:28 AM
	 */
	@RequestMapping("/list")
	public String list() {
		return "sys/user";
	}

	/**
	 * 查询用户信息
	 * 
	 * @author zhanggd
	 * @param page
	 * @param rows
	 * @return
	 * @throws  
	 * Jun 17, 2016-10:06:19 AM
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public Map<String, Object> dataGrid(String page,String rows) {
		//当前页  
        int intPage = Integer.parseInt((page == null || page == "0") ? "1":page);  
        //每页显示条数  
        int number = Integer.parseInt((rows == null || rows == "0") ? "10":rows);  
        //每页的开始记录  第一页为1  第二页为number + 1   
        int start = (intPage-1)*number;  
        
        List<User> list = userService.listAllUsers(start, number);
        int listCount = userService.listAllUsersCount();
        
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", list);
		map.put("total", listCount);
		return map;
	}

	/**
	 * 增加用户信息
	 * 
	 * @author zhanggd
	 * @param request
	 * @param response
	 * @param user
	 * @throws  
	 * Jun 16, 2016-2:55:38 PM
	 */
	@RequestMapping("/addUser")
	public void addUser(HttpServletRequest request, HttpServletResponse response, User user) {
		user.setPassword(new Md5Hash(user.getPassword(), user.getLoginName(), 1).toHex());
		userService.addUser(user);
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
	 * 编辑用户信息
	 * 
	 * @author zhanggd
	 * @param response
	 * @param user
	 * @param id
	 * @throws  
	 * Jun 16, 2016-2:56:07 PM
	 */
	@RequestMapping("/editUser")
	public void editUser(HttpServletResponse response, User user,  @RequestParam("id") int id) {
		user.setId(id);
		userService.editUser(user);
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
	 * 删除用户信息
	 * 
	 * @author zhanggd
	 * @param response
	 * @param id
	 * @throws  
	 * Jun 16, 2016-2:56:39 PM
	 */
	@RequestMapping("/deleteUser")
	public void deleteUser(HttpServletResponse response, int id) {
		userService.deleteUserById(id);
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
	 * 修改用户密码
	 * 
	 * @author zhanggd
	 * @param response
	 * @param newPass
	 * @param loginName
	 * @throws  
	 * Jun 3, 2016-3:50:52 PM
	 */
	@RequestMapping("/editUserPass")
	public void editUserPass(HttpServletResponse response, String newPass, String loginName) {
		User user = userService.findUserByLoginName(loginName);
		user.setPassword(new Md5Hash(newPass, loginName, 1).toHex());
		userService.editUserPass(user);
	}
	
	/**
	 * 查询用户树列表
	 * 
	 * @author zhanggd
	 * @return
	 * @throws  
	 * Jun 22, 2016-9:41:15 AM
	 */
	@RequestMapping("/userTree")
	@ResponseBody
	public List<Map<String, Object>> userTree() {
		List<Map<String, Object>> list = userService.userTree();
		return list;  
	}
}