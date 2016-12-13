package com.zhang.sys.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhang.sys.dao.RoleMapper;
import com.zhang.sys.domain.Role;

/**
 * 角色管理-Service
 * @author Brian.Zhang
 * Jun 17, 2016-10:42:09 AM
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class RoleService {

	@Autowired
	private RoleMapper roleMapper;
	
	/**
	 * 获取所有Role对象
	 * 
	 * @author zhanggd
	 * @param start
	 * @param number
	 * @return
	 * @throws  
	 * May 30, 2016-3:53:08 PM
	 */
	@Transactional(readOnly = true)
	public List<Role> listAllRoles(int start, int number) {
		return roleMapper.listAllRoles(start, number);
	}
	
	/**
	 * 获取所有Role对象条数
	 * 
	 * @author zhanggd
	 * @return
	 * @throws  
	 * May 30, 2016-4:26:32 PM
	 */
	@Transactional(readOnly = true)
	public int listAllRolesCount() {
		return roleMapper.listAllRolesCount();
	}
	
	/**
	 * 增加Role对象
	 * 
	 * @author zhanggd
	 * @param role
	 * @throws  
	 * Jun 17, 2016-10:46:26 AM
	 */
	public void addRole(Role role) {
		roleMapper.addRole(role);
	}

	/**
	 * 编辑Role对象
	 * 
	 * @author zhanggd
	 * @param role
	 * @throws  
	 * Jun 17, 2016-10:54:14 AM
	 */
	public void editRole(Role role) {
		roleMapper.editRole(role);
	}

	/**
	 * 删除Role对象
	 * 
	 * @author zhanggd
	 * @param id
	 * @throws  
	 * Jun 17, 2016-10:56:12 AM
	 */
	public void deleteRoleById(int id) {
		roleMapper.deleteRoleById(id);
	}
	
	/**
	 * 保存用户和角色的关系
	 * 
	 * @author zhanggd
	 * @param userId
	 * @param roleIds
	 * @throws  
	 * Jul 26, 2016-4:38:26 PM
	 */
	public void saveRolesByUserId(String userId, String roleIds) {
		// 1.先清空
		roleMapper.deleteRoleByUserId(userId);
		// 2.再保存
		String[] roles = roleIds.split(",");
		for(int i=0; i<roles.length; i++) {
			roleMapper.saveRoleByUserId(userId, roles[i]);
		}
	}
	
	/**
	 * 根据用户ID获取角色树列表
	 * 
	 * @author zhanggd
	 * @return
	 * @throws  
	 * Jun 17, 2016-2:25:27 PM
	 */
	@Transactional(readOnly = true)
	public List<Map<String, Object>> roleTreeByUserId(String userId) {
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		List<Role> list = roleMapper.roleTreeByUserId(userId);
		for (Role role : list ) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", role.getId());
			map.put("text", role.getName());
			if ( role.getUserId() != null ) {
				map.put("checked", true);
			} else {
				map.put("checked", false);
			}
			returnList.add(map);
		}
		return returnList;
	}

	/**
	 * 根据用户登录名获取所有角色
	 * 
	 * @author zhanggd
	 * @param loginName
	 * @return
	 * @throws  
	 * Aug 3, 2016-10:34:40 AM
	 */
	@Transactional(readOnly = true)
	public List<Role> findRolesByLoginName(String loginName) {
		return roleMapper.findRolesByLoginName(loginName);
	}
	
}