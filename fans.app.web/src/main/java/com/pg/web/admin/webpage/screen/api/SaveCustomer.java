package com.pg.web.admin.webpage.screen.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Params;
import com.pg.biz.manager.CustomerManager;
import com.pg.dal.model.CustomerDO;
import com.victor.framework.common.shared.Result;
import com.victor.framework.common.tools.MD5;

public class SaveCustomer {
	@Autowired
	private CustomerManager customerManager;
	
	@Autowired
	private HttpSession session;
	
	public Result<Boolean> execute(@Params CustomerDO customerDO){
		boolean exist = checkMobile(customerDO.getMobile());
		if(exist){
			return Result.newInstance(false, "该手机号码已经被注册", false);
		}
		String password = customerDO.getPassword();
		password = MD5.getMD5(password);
		customerDO.setPassword(password);
		customerManager.create(customerDO);
		return Result.newInstance(true, "注册成功", true);
	}
	
	private Boolean checkMobile(String mobile){
		return customerManager.checkExist(mobile);
	}
}
