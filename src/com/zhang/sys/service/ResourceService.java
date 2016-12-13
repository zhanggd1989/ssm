package com.zhang.sys.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhang.sys.dao.ResourceMapper;
import com.zhang.sys.domain.Resource;

/**
 * 资源管理-Service
 * @author Brian.Zhang
 * Jun 24, 2016-11:15:21 AM
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class ResourceService {

	@Autowired
	private ResourceMapper resourceMapper;
	
	/**
	 * 获取所有Resource对象
	 * 
	 * @author zhanggd
	 * @param number 
	 * @param start 
	 * @return
	 * @throws  
	 * May 27, 2016-9:20:25 AM
	 */
	@Transactional(readOnly = true)
	public List<Map<String, Object>> listAllResources() {
		return doTreeData("0", "");
	}
	
	/**
	 * 增加Resource对象
	 * 
	 * @author zhanggd
	 * @param resource
	 * @throws  
	 * Jun 24, 2016-11:18:09 AM
	 */
	public void addResource(Resource resource) {
		resourceMapper.addResource(resource);
	}

	/**
	 * 编辑Resource对象
	 * 
	 * @author zhanggd
	 * @param resource
	 * @throws  
	 * Jun 24, 2016-11:19:23 AM
	 */
	public void editResource(Resource resource) {
		resourceMapper.editResource(resource);
	}

	/**
	 * 删除Resource对象
	 * 
	 * @author zhanggd
	 * @param id
	 * @throws  
	 * Jun 24, 2016-11:19:56 AM
	 */
	public void deleteResourceById(int id) {
		resourceMapper.deleteResourceById(id);
	}

	/**
	 * 保存角色和资源的关系
	 * 
	 * @author zhanggd
	 * @param roleId
	 * @param resourceIds
	 * @throws  
	 * Jul 27, 2016-9:13:24 AM
	 */
	public void saveResourcesByRoleId(String roleId, String resourceIds) {
		// 1.先清空
		resourceMapper.deleteResouceByRoleId(roleId);
		// 2.再保存
		String[] resouces = resourceIds.split(",");
		for(int i=0; i<resouces.length; i++) {
			resourceMapper.saveResourceByRoleId(roleId, resouces[i]);
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
	@Transactional(readOnly = true)
	public List<Map<String, Object>> resourceTreeByRoleId(String roleId) {
		return doTreeData("0", roleId);
	}
	
	/**
	 * 迭代
	 */
	@Transactional(readOnly = true)
	public List<Map<String, Object>> doTreeData(String parentId, String roleId) {
		ArrayList<Map<String, Object>> newlist = new ArrayList<Map<String, Object>>();
		
		List<Resource> list = resourceMapper.findResourcesByParentId(parentId);
		for (int i = 0; i < list.size(); i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			Resource resource = list.get(i);
			map.put("id", resource.getId());
			map.put("name", resource.getName());
			map.put("text", resource.getName());
			map.put("parent", resource.getParent());
			map.put("type", resource.getType());
			map.put("href", resource.getHref());
			map.put("permission", resource.getPermission());
			map.put("useFlag", resource.getUseFlag());
			map.put("remarks", resource.getRemarks());
			if (roleId != "") {
				if ( resource.getRoleId() != null && resource.getRoleId().equals(roleId) ) {
					map.put("checked", true);
				} else {
					map.put("checked", false);
				}
			}
			String node = String.valueOf(resource.getId());

			Long count = resourceMapper.findResourceCountsByParentId(parentId);
			if (count > 0) {
				// 该节点下有子节点
				List<Map<String, Object>> listchild = this.doTreeData(node, roleId);
				map.put("children", listchild);
				map.put("state", "open");
			} else {
				// 无子节点
			}
			newlist.add(map);
		}
		return newlist;
	}

	/**
	 * 根据角色ID获取所有资源
	 * 
	 * @author zhanggd
	 * @param roleId
	 * @return
	 * @throws  
	 * Aug 4, 2016-8:51:49 AM
	 */
	public List<Resource> findResourcesByRoleId(int roleId) {
		return resourceMapper.findResourcesByRoleId(roleId);
	}
}