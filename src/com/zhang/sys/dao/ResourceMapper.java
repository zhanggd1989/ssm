package com.zhang.sys.dao;

import java.util.List;

import com.zhang.sys.domain.Resource;


public interface ResourceMapper {
	
	/**
	 * 增加Resource对象
	 * 
	 * @author zhanggd
	 * @param resource
	 * @throws  
	 * Jun 24, 2016-1:39:18 PM
	 */
	public void addResource(Resource resource);
	
	/**
	 * 编辑Resource对象
	 * 
	 * @author zhanggd
	 * @param resource
	 * @throws  
	 * Jun 24, 2016-1:39:31 PM
	 */
	public void editResource(Resource resource);
	
	/**
	 * 删除Resource对象
	 * 
	 * @author zhanggd
	 * @param id
	 * @throws  
	 * Jun 24, 2016-1:39:42 PM
	 */
	public void deleteResourceById(int id);
	
	/**
	 * 删除角色和资源的关系
	 * 
	 * @author zhanggd
	 * @param roleId
	 * @throws  
	 * Jul 27, 2016-10:00:19 AM
	 */
	public void deleteResouceByRoleId(String roleId);

	/**
	 * 保存角色和资源的关系
	 * 
	 * @author zhanggd
	 * @param roleId
	 * @param string
	 * @throws  
	 * Jul 27, 2016-10:01:43 AM
	 */
	public void saveResourceByRoleId(String roleId, String resourceId);
	
	/**
	 * 根据父级ID获取Resource对象
	 * 
	 * @author zhanggd
	 * @param parentid
	 * @return
	 * @throws  
	 * Jul 27, 2016-9:56:27 AM
	 */
	public List<Resource> findResourcesByParentId(String parentId);

	/**
	 * 根据父级ID获取Resource对象条数
	 * 
	 * @author zhanggd
	 * @param node
	 * @return
	 * @throws  
	 * Jul 27, 2016-9:58:22 AM
	 */
	public Long findResourceCountsByParentId(String parentId);

	/**
	 * 根据角色ID获取所有资源
	 * 
	 * @author zhanggd
	 * @param roleId
	 * @return
	 * @throws  
	 * Aug 4, 2016-9:37:06 AM
	 */
	public List<Resource> findResourcesByRoleId(int roleId);

}