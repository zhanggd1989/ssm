package com.zhang.sys.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhang.sys.domain.Organization;
import com.zhang.sys.service.OrganizationService;

import net.sf.json.JSONObject;

/**
 * 机构管理-Controller
 * @author Brian.Zhang
 * Jun 22, 2016-9:53:38 AM
 */
@RequestMapping("/organization")
@Controller
public class OrganizationController {
	
	@Autowired
	private OrganizationService organizationService;

	/**
	 * 机构信息主界面
	 * 
	 * @author zhanggd
	 * @return
	 * @throws  
	 * Jun 20, 2016-2:40:14 PM
	 */
	@RequestMapping("/list")
	public String list() {
		return "sys/organization";
	}
	
	/**
	 * 查询机构信息
	 * @author zhanggd
	 * @param page
	 * @param rows
	 * @return
	 * @throws  
	 * Jun 20, 2016-2:40:23 PM
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public Map<String, Object> dataGrid() {
        List<Map<String, Object>> list = organizationService.listAllOrganizations();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", list);
		return map;
	}
	
	/**
	 * 增加机构信息
	 * 
	 * @author zhanggd
	 * @param response
	 * @param office
	 * @throws  
	 * Jun 22, 2016-10:03:11 AM
	 */
	@RequestMapping("/addOrganization")
	public void addOrganization(HttpServletResponse response, Organization organization) {
		organizationService.addOrganization(organization);
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
	 * 编辑机构信息
	 * 
	 * @author zhanggd
	 * @param response
	 * @param role
	 * @param id
	 * @throws  
	 * Jun 22, 2016-10:07:54 AM
	 */
	@RequestMapping("/editOrganization")
	public void editOrganization(HttpServletResponse response, Organization organization,  @RequestParam("id") int id) {
		organization.setId(id);;
		organizationService.editOrganization(organization);
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
	 * 删除机构信息
	 * 
	 * @author zhanggd
	 * @param response
	 * @param id
	 * @throws  
	 * Jun 22, 2016-10:11:39 AM
	 */
	@RequestMapping("/deleteOrganization")
	public void deleteOrganization(HttpServletResponse response, int id) {
		organizationService.deleteOrganizationById(id);
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
	 * 获取单位树列表
	 * 
	 * @author zhanggd
	 * @return
	 * @throws  
	 * Jun 20, 2016-2:42:54 PM
	 */
	@RequestMapping("organizationTree")
	@ResponseBody
	public List<Map<String, Object>> organizationTree() {
		List<Map<String, Object>> list = organizationService.organizationTree();
		return list;  
	}
	
	/**
	 * 根据单位ID获取部门树列表
	 * 
	 * @author zhanggd
	 * @param companyId
	 * @return
	 * @throws  
	 * Jul 26, 2016-1:22:17 PM
	 */
	@RequestMapping("departmentTreeByCompanyId")
	@ResponseBody
	public List<Map<String, Object>> departmentTreeByCompanyId(@RequestParam("companyId") String companyId) {
		List<Map<String, Object>> list = organizationService.departmentTreeByCompanyId(companyId);
		return list;  
	}
}