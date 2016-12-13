package com.zhang.sys.dao;

import java.util.List;

import com.zhang.sys.domain.User;

/**
 * 用户管理-Mapper
 * @author Brian.Zhang 
 * Jun 16, 2016-3:25:11 PM
 */
public interface UserMapper {

	/**
	 * 获取所有User对象
	 * 
	 * @author zhanggd
	 * @param start
	 * @param number
	 * @return
	 * @throws 
	 * Jun 16, 2016-3:31:48 PM
	 */
	public List<User> listAllUsers(int start, int number);

	/**
	 * 获取所有User对象条数
	 * 
	 * @author zhanggd
	 * @return
	 * @throws 
	 * Jun 16, 2016-3:32:10 PM
	 */
	public int listAllUsersCount();

	/**
	 * 增加User对象
	 * 
	 * @author zhanggd
	 * @param user
	 * @return
	 * @throws 
	 * Jun 16, 2016-3:32:39 PM
	 */
	public int addUser(User user);

	/**
	 * 编辑User对象
	 * 
	 * @author zhanggd
	 * @param user
	 * @throws 
	 * Jun 16, 2016-3:32:46 PM
	 */
	public void editUser(User user);

	/**
	 * 删除User对象
	 * 
	 * @author zhanggd
	 * @param id
	 * @throws 
	 * Jun 16, 2016-3:32:58 PM
	 */
	public void deleteUserById(int id);
	
	/**
	 * 根据登录名获取User对象
	 * 
	 * @author zhanggd
	 * @param loginName
	 * @return
	 * @throws 
	 * Jun 16, 2016-3:32:24 PM
	 */
	public User findUserByLoginName(String loginName);

	/**
	 * 修改User对象的密码
	 * 
	 * @author zhanggd
	 * @param user
	 * @throws 
	 * Jun 16, 2016-3:33:29 PM
	 */
	public void editUserPass(User user);

	/**
	 * 查询用户树列表
	 * 
	 * @author zhanggd
	 * @return
	 * @throws 
	 * Jun 22, 2016-9:43:30 AM
	 */
	public List<User> userTree();

}