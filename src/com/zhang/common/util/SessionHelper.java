package com.zhang.common.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

public class SessionHelper {

	/**
	 * 设置Session
	 */
	public static void setSession(Object key, Object value) {  
        Subject currentUser = SecurityUtils.getSubject();  
        if (null != currentUser) {  
        	Session session = currentUser.getSession();  
	        if (null != session) {  
	        	session.setAttribute(key, value);  
            }  
        }  
    }
	
	/**
	 * 获取Session
	 */
	public static Session getSession() {  
        return SecurityUtils.getSubject().getSession();  
    }
}
