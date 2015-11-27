package com.pg.web.admin.webpage.screen.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.pg.biz.manager.CustomerManager;
import com.pg.dal.model.CustomerDO;
import com.pg.web.admin.common.AuthenticationToken;
import com.victor.framework.common.shared.Result;
import com.victor.framework.common.tools.MD5;

public class UpdatePassword {
	@Autowired
	private CustomerManager customerManager;
	
	@Autowired
	private HttpSession session;
	
	public Result<Boolean> execute(	@Param("oldPwd") String oldPwd, 
									@Param("newPwd")String newPwd){
		CustomerDO customer = AuthenticationToken.getLoginedCustomer(session);
		if(customer == null){
			return Result.newInstance(false, "登陆已失效", false);
		}
		Result<CustomerDO> result = customerManager.login(customer.getMobile(), oldPwd);
		if(!result.isSuccess()){
			return Result.newInstance(false, result.getMessage(), false);
		}
		customer.setPassword(MD5.getMD5(newPwd));
		customerManager.update(customer);
		AuthenticationToken.set(session, customer);
		return Result.newInstance(true, "更新成功", true);
	}
}
