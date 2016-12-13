package com.zhang.common.realm;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhang.common.util.SessionHelper;
import com.zhang.sys.domain.Resource;
import com.zhang.sys.domain.Role;
import com.zhang.sys.domain.User;
import com.zhang.sys.service.ResourceService;
import com.zhang.sys.service.RoleService;
import com.zhang.sys.service.UserService;


public class UserRealm extends AuthorizingRealm {

	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;

	@Autowired
	private ResourceService resourceService;
	
	/**
	 * 授权认证
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// 获取当前登录用户的相关信息
		String loginName = (String) principals.fromRealm(this.getName()).iterator().next();
		
		// 角色列表
		List<String> roleList = new ArrayList<String>();
		// 权限列表
		List<String> permissionList = new ArrayList<String>();
		
		List<Role> roles = roleService.findRolesByLoginName(loginName);
		for (Role role : roles) {
			roleList.add(role.getName());
			int roleId = role.getId();
			List<Resource> resources = resourceService.findResourcesByRoleId(roleId);
			for (Resource resource : resources) {
				permissionList.add(resource.getName());
			}
		}
		// 为当前用户设置角色和权限
		SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
		simpleAuthorInfo.addRoles(roleList);
		simpleAuthorInfo.addStringPermissions(permissionList);
		return simpleAuthorInfo;
	}

	/**
	 * 登录认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		User user = userService.findUserByLoginName(token.getUsername());
		SessionHelper.setSession("userInfo", user);
		if (null != user) {
			SimpleAuthenticationInfo authcInfo = new SimpleAuthenticationInfo(user.getLoginName(), user.getPassword(), user.getRealName());
			authcInfo.setCredentialsSalt(ByteSource.Util.bytes(user.getLoginName()));
			return authcInfo;
		} else {
			return null;
		}
	}
	
}