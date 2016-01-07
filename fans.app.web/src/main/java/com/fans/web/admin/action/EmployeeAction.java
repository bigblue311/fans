package com.fans.web.admin.action;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.dataresolver.FormGroup;
import com.fans.admin.common.AuthenticationToken;
import com.fans.biz.manager.EmployeeManager;
import com.fans.dal.model.EmployeeDO;
import com.victor.framework.common.shared.Result;
import com.victor.framework.common.tools.MD5;

public class EmployeeAction {
	
	@Autowired
    private HttpSession session;
	
	@Autowired
	private EmployeeManager employeeManager;
	
	public void doLogin(@FormGroup("login") EmployeeDO employeeDO, Navigator nav) {
		Result<EmployeeDO> result = employeeManager.login(employeeDO);
		if(result.isSuccess()){
			AuthenticationToken.set(session, result.getDataObject());
			nav.redirectTo("admin").withTarget("welcome.vm");
		} else {
			nav.redirectTo("admin").withTarget("login.vm");
		}
	}
	
	public void doChangePwd(@FormGroup("changePwd")EmployeeDO employeeDO,Navigator nav){
		String password = MD5.getMD5(employeeDO.getPassword());
		employeeDO.setPassword(password);
		employeeManager.update(employeeDO);
		AuthenticationToken.expireUser(session);
		nav.redirectTo("admin").withTarget("login.vm");
	}
	
	public void doLogout(Navigator nav){
		AuthenticationToken.expireUser(session);
		nav.redirectTo("admin").withTarget("login.vm");
	}
	
	public void doUpdate(@FormGroup("employee") EmployeeDO employeeDO) {
		if(employeeDO.getId() == null ) {
			String name = employeeDO.getName();
			if(!employeeManager.checkExist(name)){
				employeeDO.setPassword(MD5.getMD5("123456"));
				employeeManager.create(employeeDO);
			}
		} else {
			employeeManager.update(employeeDO);
		}
	}
	
	public void doResetPwd(@FormGroup("employee") EmployeeDO employeeDO) {
		String password = MD5.getMD5(employeeDO.getPassword());
		employeeDO.setPassword(password);
		employeeManager.update(employeeDO);
	}
	
	public void doDelete(@FormGroup("employee") EmployeeDO employeeDO) {
		employeeManager.delete(employeeDO.getId());
	}
}
