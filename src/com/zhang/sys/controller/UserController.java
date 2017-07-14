package com.zhang.sys.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhang.sys.domain.Msg;
import com.zhang.sys.domain.User;
import com.zhang.sys.service.UserService;
import net.sf.json.JSONObject;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户管理-Controller
 *
 * @author Brian.Zhang
 * Jun 15, 2016-4:04:10 PM
 */
@RequestMapping("/user")
@Controller
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * 用户主界面
	 *
	 */
	@RequestMapping("/list")
	public String list() {
		return "sys/user";
	}

	/**
	 * 查询用户信息
	 *
	 */
	@RequestMapping("/getUsers")
	@ResponseBody
	public Msg getUsers(@RequestParam(value="page") Integer page) {
		//当前页
		int intPage = (page == null || page == 0) ? 1 : page;
		//每页显示条数
        int number = 10;
		PageHelper.startPage(intPage, number);
		List<User> userList = userService.listAllUsers();
		PageInfo userPage = new PageInfo(userList, number);
		return Msg.success().add("userPage", userPage);
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
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	@ResponseBody
	public Msg addUser( User user,BindingResult result) {
		if(result.hasErrors()) {
			Map<String, Object> map = new HashMap<String, Object>();
			List<FieldError> errors = result.getFieldErrors();
			for(FieldError fieldError : errors) {
				System.out.println("错误的字段名 :" + fieldError.getField());
				System.out.println("错误信息 :" + fieldError.getDefaultMessage());
				map.put(fieldError.getField(),fieldError.getDefaultMessage());
			}
			return Msg.failure().add("fieldErrors",map);
		} else {
			user.setPassword(new Md5Hash(user.getPassword(), user.getLoginName(), 1).toHex());
			userService.addUser(user);
			return Msg.success();
		}
	}

	@RequestMapping(value = "/checkUser")
	@ResponseBody
	public Msg checkUser(String loginName) {
		String regx = "^[a-zA-Z0-9_-]{3,16}$";
		if(!loginName.matches(regx)){
			return Msg.failure().add("va_msg","用户名必须是3到16位的字母与数字组合");
		}
		User user = userService.findUserByLoginName(loginName);
		if(null != user){
			return Msg.failure();
		} else {
			return Msg.success();
		}

	}
	@RequestMapping(value="/getUserById/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Msg getUserById(@PathVariable int id){
		User user = userService.getUserById(id);
		return Msg.success().add("user",user);
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
	@RequestMapping(value = "/editUser/{id}",method = RequestMethod.POST)
	public Msg editUser(User user, @PathVariable int id) {
			user.setId(id);
		userService.editUser(user);
		return Msg.success();
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