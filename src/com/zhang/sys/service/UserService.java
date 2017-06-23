package com.zhang.sys.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhang.sys.dao.UserMapper;
import com.zhang.sys.domain.User;

/**
 * 用户管理-Service
 * @author Brian.Zhang
 * Jun 16, 2016-3:06:03 PM
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class UserService {

	@Autowired
	private UserMapper userMapper;
	
	/**
	 * 获取所有User对象
	 * 
	 * @author zhanggd
	 * @param start
	 * @param number
	 * @return
	 * @throws  
	 * Jun 16, 2016-3:21:57 PM
	 */
	@Transactional(readOnly = true)
	public List<User> listAllUsers(int start, int number) {
		return userMapper.listAllUsers(start, number);
	}

	public List<User> listAllUsers1() {
		return userMapper.listAllUsers1();
	}

	/**
	 * 获取所有User对象条数
	 * 
	 * @author zhanggd
	 * @return
	 * @throws  
	 * Jun 16, 2016-3:22:05 PM
	 */
	@Transactional(readOnly = true)
	public int listAllUsersCount() {
		return userMapper.listAllUsersCount();
	}
	
	/**
	 * 增加User对象
	 * 
	 * @author zhanggd
	 * @param user
	 * @throws  
	 * Jun 16, 2016-3:22:51 PM
	 */
	public void addUser(User user) {
		userMapper.addUser(user);
	}

	/**
	 * 编辑User对象
	 *
	 * @author zhanggd
	 * @param user
	 * @throws
	 * Jun 16, 2016-3:23:12 PM
	 */
	public void editUser(User user) {
		userMapper.editUser(user);
	}

	/**
	 * 删除User对象
	 * 
	 * @author zhanggd
	 * @param id
	 * @throws  
	 * Jun 16, 2016-3:23:50 PM
	 */
	public void deleteUserById(int id) {
		userMapper.deleteUserById(id);
	}

	/**
	 * 根据登录名获取User对象
	 * 
	 * @author zhanggd
	 * @param loginName
	 * @return
	 * @throws  
	 * Jun 16, 2016-3:22:20 PM
	 */
	@Transactional(readOnly = true)
	public User findUserByLoginName(String loginName) {
		return userMapper.findUserByLoginName(loginName);
	}
	
	/**
	 * 修改User对象的密码
	 * 
	 * @author zhanggd
	 * @param user
	 * @throws  
	 * Jun 16, 2016-3:24:48 PM
	 */
	public void editUserPass(User user) {
		userMapper.editUserPass(user);
	}
	
	/**
	 * 查询用户树列表
	 * 
	 * @author zhanggd
	 * @return
	 * @throws  
	 * Jun 22, 2016-9:42:35 AM
	 */
	@Transactional(readOnly = true)
	public List<Map<String, Object>> userTree() {
		List<Map<String, Object>>  returnList = new ArrayList<Map<String, Object>>();
		List<User> list = userMapper.userTree();
		for (User user : list ) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", user.getId());
			map.put("text", user.getRealName());
			returnList.add(map);
		}
		return returnList;
	}
}