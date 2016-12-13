package com.zhang.login.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.sf.json.JSONObject;

@Controller
public class LoginController {

	/**
	 * 登陆
	 * 
	 * @author zhanggd
	 * @return
	 * @return String
	 * @throws 2015年12月18日 下午2:06:43
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "login/login";
	}

	/**
	 * 首页
	 * 
	 * @author zhanggd
	 * @param request
	 * @param model
	 * @return
	 * @throws ParseException
	 * @return String
	 * @throws 
	 * 2016年3月23日 下午1:29:40
	 */
	@RequestMapping(value = "/index", method = RequestMethod.POST)
	public String index(HttpServletRequest request, Model model)
			throws ParseException {
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String msg = "";
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
		try {
			subject.login(token);
			if (subject.isAuthenticated()) {
				model.addAttribute("loginName",userName);
				return "index";
			} else {
				return "login";
			}
		} catch (IncorrectCredentialsException e) {
			msg = "登录密码错误. Password for account " + token.getPrincipal() + " was incorrect.";
		} catch (ExcessiveAttemptsException e) {
			msg = "登录失败次数过多";
		} catch (LockedAccountException e) {
			msg = "帐号已被锁定. The account for username " + token.getPrincipal() + " was locked.";
		} catch (DisabledAccountException e) {
			msg = "帐号已被禁用. The account for username " + token.getPrincipal() + " was disabled.";
		} catch (ExpiredCredentialsException e) {
			msg = "帐号已过期. the account for username " + token.getPrincipal() + "  was expired.";
		} catch (UnknownAccountException e) {
			msg = "帐号不存在. There is no user with username of " + token.getPrincipal();
		} catch (UnauthorizedException e) {
			msg = "您没有得到相应的授权！" + e.getMessage();
		} finally {
			model.addAttribute("message", msg);
		}
		return "login";
	}
	
	/**
	 * 退出
	 * 
	 * @author zhanggd
	 * @param response
	 * @param session
	 * @throws  
	 * Jun 3, 2016-3:13:59 PM
	 */
	@RequestMapping("/logout")
	public void logout(HttpServletResponse response, HttpSession session) {
		//session.invalidate();
		JSONObject object = new JSONObject();
		object.put("successMsg", "退出成功！");
		object.put("success", true);
		try {
			PrintWriter out = response.getWriter();
			out.write(object.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}