package com.zhang.sys.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhang.sys.dao.OrganizationMapper;
import com.zhang.sys.domain.Organization;


/**
 * 机构管理-Service
 * @author Brian.Zhang
 * Jun 22, 2016-10:05:01 AM
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class OrganizationService {

	@Autowired
	private OrganizationMapper organizationMapper;

	/**
	 * 获取所有Organization对象
	 * 
	 * @author zhanggd
	 * @param start
	 * @param number
	 * @return
	 * @throws  
	 * Jul 25, 2016-9:39:50 AM
	 */
	@Transactional(readOnly = true)
	public List<Map<String, Object>> listAllOrganizations() {
		return doTreeData("0", "2");
	}
	
	/**
	 * 增加Organization对象
	 * 
	 * @author zhanggd
	 * @param organization
	 * @throws  
	 * Jun 22, 2016-10:05:22 AM
	 */
	public void addOrganization(Organization organization) {
		organizationMapper.addOrganization(organization);
	}
	
	/**
	 * 编辑Organization对象
	 * 
	 * @author zhanggd
	 * @param organization
	 * @throws  
	 * Jun 22, 2016-10:10:50 AM
	 */
	public void editOrganization(Organization organization) {
		organizationMapper.editOrganization(organization);
	}
	
	/**
	 * 删除Organization对象
	 * 
	 * @author zhanggd
	 * @param id
	 * @throws  
	 * Jun 22, 2016-10:12:32 AM
	 */
	public void deleteOrganizationById(int id) {
		organizationMapper.deleteOrganizationById(id);
	}
	
	/**
	 * 获取单位树列表
	 * 
	 * @author zhanggd
	 * @return
	 * @throws  
	 * Jul 26, 2016-9:39:41 AM
	 */
	@Transactional(readOnly = true)
	public List<Map<String, Object>> organizationTree() {
		return doTreeData("0", "0");
	}
	
	/**
	 * 根据单位ID获取部门树列表
	 * 
	 * @author zhanggd
	 * @param companyId
	 * @return
	 * @throws  
	 * Jul 26, 2016-1:23:22 PM
	 */
	@Transactional(readOnly = true)
	public List<Map<String, Object>> departmentTreeByCompanyId(String companyId) {
		List<Organization> list = organizationMapper.departmentTreeByCompanyId(companyId);
		ArrayList<Map<String, Object>> newlist = new ArrayList<Map<String, Object>>();
		
		for (int i = 0; i < list.size(); i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			Organization organization = list.get(i);
			map.put("id", organization.getId());
			map.put("text", organization.getName());
			newlist.add(map);
		}
		return newlist;
	}
	
	/**
	 * 迭代
	 */
	@Transactional(readOnly = true)
	public List<Map<String, Object>> doTreeData(String parentId, String type) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("parentId", parentId);
		param.put("type", type);
		List<Organization> list = organizationMapper.findOrganizationsByParentId(param);
		ArrayList<Map<String, Object>> newlist = new ArrayList<Map<String, Object>>();

		for (int i = 0; i < list.size(); i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			Organization organization = list.get(i);
			map.put("id", organization.getId());
			map.put("name", organization.getName());
			map.put("text", organization.getName());
			map.put("parent", organization.getParent());
			map.put("type", organization.getType());
			map.put("postcode", organization.getPostcode());
			map.put("address", organization.getAddress());
			map.put("master", organization.getMaster() == null ? "" : organization.getMaster());
			map.put("useFlag", organization.getUseFlag());
			map.put("remarks", organization.getRemarks());

			String node = String.valueOf(organization.getId());

			param.put("parentId", parentId);
			Long count = organizationMapper.findOrganizationCountsByparentId(param);
			if (count > 0) {
				// 该节点下有子节点
				List<Map<String, Object>> listchild = this.doTreeData(node, type);
				map.put("children", listchild);
				map.put("state", "open");
			} else {
				// 无子节点
			}
			newlist.add(map);
		}
		return newlist;
	}
}