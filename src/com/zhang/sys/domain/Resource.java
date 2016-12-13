package com.zhang.sys.domain;

import java.io.Serializable;

public class Resource implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9111848023177190173L;

	private int id;// 资源编号
	private Resource parent;// 父级资源
	private String parentIds;// 所有父级编号
	private String name;// 资源名称
	private String type;// 资源类型
	private String href;// 资源链接
	private String permission;// 资源权限
	private String useFlag;// 是否可用
	private String remarks;// 备注
	private String delFlag;// 删除标志
	
	private String roleId;// 角色ID

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Resource getParent() {
		return parent;
	}

	public void setParent(Resource parent) {
		this.parent = parent;
	}

	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getUseFlag() {
		return useFlag;
	}

	public void setUseFlag(String useFlag) {
		this.useFlag = useFlag;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@Override
	public String toString() {
		return "Resource [id=" + id + ", parent=" + parent + ", parentIds=" + parentIds + ", name=" + name + ", type="
				+ type + ", href=" + href + ", permission=" + permission + ", useFlag=" + useFlag + ", remarks="
				+ remarks + ", delFlag=" + delFlag + ", roleId=" + roleId + "]";
	}

}