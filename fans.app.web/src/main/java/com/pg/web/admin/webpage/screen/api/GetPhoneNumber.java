package com.pg.web.admin.webpage.screen.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.pg.biz.manager.EmployeeManager;
import com.pg.dal.cache.SystemConfigCache;
import com.pg.dal.enumerate.SystemConfigKeyEnum;
import com.pg.dal.model.CustomerDO;
import com.pg.dal.model.EmployeeDO;
import com.pg.dal.model.SystemConfigDO;
import com.pg.web.admin.common.AuthenticationToken;
import com.victor.framework.common.shared.Result;

public class GetPhoneNumber {
	@Autowired
	private EmployeeManager employeeManager;
	
	@Autowired
	private SystemConfigCache systemConfigCache;
	
	@Autowired
    private HttpSession session;
	
	public Result<String> execute() {
		String phoneNumber = "";
		CustomerDO customerDO = AuthenticationToken.getLoginedCustomer(session);
		if(customerDO == null){
			phoneNumber = getSystemDefault();
		} else {
			Long id = customerDO.getEmployeeId();
			EmployeeDO employeeDO = employeeManager.getById(id);
			if(employeeDO != null){
				phoneNumber = employeeDO.getPhone();
			} else {
				phoneNumber = getSystemDefault();
			}
		}
		return Result.newInstance(phoneNumber, "获取电话成功", true);
	}
	
	private String getSystemDefault(){
		SystemConfigDO systemConfigDO = systemConfigCache.getCache(SystemConfigKeyEnum.SYSTEM_HOT_LINE.getCode());
		if(systemConfigDO != null){
			return systemConfigDO.getConfigValue();
		}
		return "";
	}
}
