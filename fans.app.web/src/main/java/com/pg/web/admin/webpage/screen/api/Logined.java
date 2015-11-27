package com.pg.web.admin.webpage.screen.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.pg.dal.model.CustomerDO;
import com.pg.web.admin.common.AuthenticationToken;
import com.victor.framework.common.shared.Result;

public class Logined {
	
	@Autowired
    private HttpSession session;
	
	public Result<CustomerDO> execute() {
		boolean logined = AuthenticationToken.customerLogined(session);
		if(logined){
			return Result.newInstance(AuthenticationToken.getLoginedCustomer(session), "", true);
		} else {
			return Result.newInstance(null, "", false);
		}
	}
}
