package com.fans.dal.query;

import java.util.Date;

import com.victor.framework.common.tools.DateTools;
import com.victor.framework.dal.basic.QueryCondition;

public class EmployeeQueryCondition extends QueryCondition {
	
	public EmployeeQueryCondition setName(String name) {
		put("name",name);
		return this;
	}
	
	public String getName(){
		return getString("name");
	}
	
	public EmployeeQueryCondition setPassword(String password) {
		put("password",password);
		return this;
	}
	
	public String getPassword(){
		return getString("password");
	}
	
	public EmployeeQueryCondition setRoleId(String roleId) {
		put("roleId",roleId);
		return this;
	}
	
	public String getRole(){
		return getString("roleId");
	}
	
	@Override
	public EmployeeQueryCondition setGmtModifyStart(Date from){
		put("gmtModifyStart", DateTools.getDayBegin(from));
		return this;
	}
	
	@Override
	public EmployeeQueryCondition setGmtModifyEnd(Date to){
		put("gmtModifyEnd", DateTools.getDayEnd(to));
		return this;
	}
	
	@Override
	public EmployeeQueryCondition setStart(int start){
		put("start", start);
		return this;
	}
	
	@Override
	public EmployeeQueryCondition setPageSize(int pageSize){
		put("pageSize", pageSize);
		return this;
	}
}
