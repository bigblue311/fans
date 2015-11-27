package com.pg.web.admin.webpage.screen.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Params;
import com.pg.biz.manager.CustomerManager;
import com.pg.dal.model.CustomerDO;
import com.pg.web.admin.common.AuthenticationToken;
import com.victor.framework.common.shared.Result;

public class UpdateCustomer {
	@Autowired
	private CustomerManager customerManager;
	
	@Autowired
	private HttpSession session;
	
	public Result<Boolean> execute(@Params CustomerDO customerDO){
		CustomerDO customer = AuthenticationToken.getLoginedCustomer(session);
		if(customer == null){
			return Result.newInstance(false, "登陆已失效", false);
		}
		if(customer.getId().intValue() != customerDO.getId()){
			return Result.newInstance(false, "登陆已失效", false);
		}
		boolean exist = checkMobile(customerDO.getMobile(),customerDO.getId());
		if(exist){
			return Result.newInstance(false, "该手机号码已经被注册", false);
		}
		customerManager.update(customerDO);
		CustomerDO updated = customerManager.getById(customerDO.getId());
		AuthenticationToken.set(session, updated);
		return Result.newInstance(true, "更新成功", true);
	}
	
	private Boolean checkMobile(String mobile, Long id){
		CustomerDO exist = customerManager.getById(id);
		if(exist.getMobile().equals(mobile)){
			return false;
		} else {
			return customerManager.checkExist(mobile);
		}
	}
}
