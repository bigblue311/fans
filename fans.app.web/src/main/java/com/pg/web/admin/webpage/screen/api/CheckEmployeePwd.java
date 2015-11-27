package com.pg.web.admin.webpage.screen.api;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Params;
import com.pg.biz.manager.EmployeeManager;
import com.pg.dal.model.EmployeeDO;
import com.victor.framework.common.shared.Result;

public class CheckEmployeePwd {
	
	@Autowired
	private EmployeeManager employeeManager;
	
	public Result<Boolean> execute(@Params EmployeeDO employeeDO) {
		return employeeManager.checkPwd(employeeDO);
	}
}
