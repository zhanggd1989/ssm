package com.zhang.sys.dao;

import java.util.List;

import com.zhang.sys.domain.Role;

/**
 * 角色管理-Mapper
 * @author Brian.Zhang
 * Jun 17, 2016-10:57:20 AM
 */
public interface RoleMapper {

	/**
	 * 获取所有Role对象
	 * 
	 * @author zhanggd
	 * @param start
	 * @param number
	 * @return
	 * @throws  
	 * May 30, 2016-4:48:42 PM
	 */
	public List<Role> listAllRoles(int start, int number);

	/**
	 * 获取所有Role对象条数
	 * 
	 * @author zhanggd
	 * @return
	 * @throws  
	 * May 30, 2016-4:50:34 PM
	 */
	public int listAllRolesCount();
	
	/**
	 * 增加Role对象
	 * 
	 * @author zhanggd
	 * @param role
	 * @throws  
	 * Jun 17, 2016-10:58:48 AM
	 */
	public void addRole(Role role);

	/**
	 * 编辑Role对象
	 * 
	 * @author zhanggd
	 * @param role
	 * @throws  
	 * Jun 17, 2016-10:59:17 AM
	 */
	public void editRole(Role role);

	/**
	 * 删除Role对象
	 * 
	 * @author zhanggd
	 * @param id
	 * @throws  
	 * Jun 17, 2016-11:00:03 AM
	 */
	public void deleteRoleById(int id);
	
	/**
	 * 根据用户ID增加User对象的所有角色
	 * 
	 * @author zhanggd
	 * @param id
	 * @param roleId
	 * @throws  
	 * Jun 17, 2016-2:31:03 PM
	 */
	void addRolesByUserId(int userId, int roleId);
	
	/**
	 * 根据用户ID获取User对象的所有角色
	 * 
	 * @author zhanggd
	 * @param id
	 * @return
	 * @throws 
	 * Jun 16, 2016-3:33:21 PM
	 */
	public List<Integer> findRolesByUserId(int userId);
	
	/**
	 * 删除用户和角色的关系
	 * 
	 * @author zhanggd
	 * @param userId
	 * @throws  
	 * Jul 26, 2016-4:50:13 PM
	 */
	public void deleteRoleByUserId(String userId);
	/**
	 * 保存用户和角色的关系
	 * 
	 * @author zhanggd
	 * @param userId
	 * @param string
	 * @throws  
	 * Jul 26, 2016-4:41:15 PM
	 */
	public void saveRoleByUserId(String userId, String string);
	
	/**
	 * 根据用户ID获取角色树列表
	 * 
	 * @author zhanggd
	 * @return
	 * @throws  
	 * Jun 17, 2016-2:30:57 PM
	 */
	List<Role> roleTreeByUserId(String userId);

	/**
	 * 根据用户登录名获取角色
	 * 
	 * @author zhanggd
	 * @param loginName
	 * @return
	 * @throws  
	 * Aug 3, 2016-10:55:04 AM
	 */
	public List<Role> findRolesByLoginName(String loginName);

}