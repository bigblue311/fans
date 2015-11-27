package com.pg.web.admin.webpage.screen.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Params;
import com.pg.biz.manager.CustomerManager;
import com.pg.dal.model.CustomerDO;
import com.pg.web.admin.common.AuthenticationToken;
import com.victor.framework.common.shared.Result;

public class Login {
	@Autowired
	private CustomerManager customerManager;
	
	@Autowired
    private HttpSession session;
	
	public Result<Boolean> execute(@Params CustomerDO customerDO) {
		Result<CustomerDO> result = customerManager.login(customerDO.getMobile(), customerDO.getPassword());
		if(result.isSuccess()){
			AuthenticationToken.set(session, result.getDataObject());
		}
		return Result.newInstance(result.isSuccess(), result.getMessage(), result.isSuccess());
	}
}
