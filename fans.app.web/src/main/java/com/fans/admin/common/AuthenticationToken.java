package com.fans.admin.common;

import javax.servlet.http.HttpSession;

import com.fans.dal.model.EmployeeDO;


public class AuthenticationToken {
	
	/**
	 * 获取登录的用户
	 * @param session
	 * @return
	 */
	public static EmployeeDO getLoginedUser(HttpSession session){
		EmployeeDO employeeDO = (EmployeeDO)session.getAttribute(SessionKey.LOGINED_USER);
		return employeeDO;
	}
	
	/**
	 * 是否登录过
	 * @param session
	 * @return
	 */
	public static boolean userLogined(HttpSession session){
		return getLoginedUser(session) != null;
	}
	
	/**
	 * 添加登录的用户
	 * @param session
	 * @param employeeDO
	 */
	public static void set(HttpSession session,EmployeeDO employeeDO){
		session.setAttribute(SessionKey.LOGINED_USER, employeeDO);
	}
	
	/**
	 * 登出
	 * @param session
	 */
	public static void expireUser(HttpSession session){
		session.removeAttribute(SessionKey.LOGINED_USER);
	}
}
