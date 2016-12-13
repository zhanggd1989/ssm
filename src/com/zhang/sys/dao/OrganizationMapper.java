package com.zhang.sys.dao;

import java.util.List;
import java.util.Map;

import com.zhang.sys.domain.Organization;


public interface OrganizationMapper {
	
	/**
	 * 增加Organization对象
	 * 
	 * @author zhanggd
	 * @param organization
	 * @throws  
	 * Jun 22, 2016-10:20:28 AM
	 */
	public void addOrganization(Organization organization);

	/**
	 * 编辑Organization对象
	 * 
	 * @author zhanggd
	 * @param organization
	 * @throws  
	 * Jun 22, 2016-10:21:40 AM
	 */
	public void editOrganization(Organization organization);

	/**
	 * 删除Organization对象
	 * 
	 * @author zhanggd
	 * @param id
	 * @throws  
	 * Jun 22, 2016-10:21:56 AM
	 */
	public void deleteOrganizationById(int id);
	
	/**
	 * 根据父级ID获取Organization对象
	 * 
	 * @author zhanggd
	 * @param param
	 * @return
	 * @throws  
	 * Jul 26, 2016-10:52:24 AM
	 */
	public List<Organization> findOrganizationsByParentId(Map<String, String> param);

	/**
	 * 根据父级ID获取Organization对象条数
	 * 
	 * @author zhanggd
	 * @param param
	 * @return
	 * @throws  
	 * Jul 26, 2016-10:52:17 AM
	 */
	public Long findOrganizationCountsByparentId(Map<String, String> param);
	
	/**
	 * 根据单位ID获取部门列表
	 * 
	 * @author zhanggd
	 * @param companyId
	 * @return
	 * @throws  
	 * Jul 26, 2016-1:30:28 PM
	 */
	public List<Organization> departmentTreeByCompanyId(String companyId);
	
}