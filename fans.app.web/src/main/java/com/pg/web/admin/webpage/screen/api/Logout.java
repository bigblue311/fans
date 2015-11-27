package com.pg.web.admin.webpage.screen.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.pg.biz.manager.CustomerManager;
import com.pg.web.admin.common.AuthenticationToken;
import com.victor.framework.common.shared.Result;

public class Logout {
	@Autowired
	private CustomerManager customerManager;
	
	@Autowired
    private HttpSession session;
	
	public Result<Boolean> execute() {
		AuthenticationToken.expireCustomer(session);
		return Result.newInstance(true, "", true);
	}
}
