package com.pg.dal.query;

import java.util.Date;

import com.victor.framework.common.tools.DateTools;
import com.victor.framework.dal.basic.QueryCondition;

public class ResourceRoleQueryCondition extends QueryCondition {
	
	public ResourceRoleQueryCondition setRoleId(Long roleId) {
		put("roleId",roleId);
		return this;
	}
	
	public Long getRoleId(){
		return getLong("roleId");
	}
	
	public ResourceRoleQueryCondition setResourceId(Long resourceId) {
		put("resourceId",resourceId);
		return this;
	}
	
	public Long getResourceId(){
		return getLong("resourceId");
	}
	
	@Override
	public ResourceRoleQueryCondition setGmtModifyStart(Date from){
		put("gmtModifyStart", DateTools.getDayBegin(from));
		return this;
	}
	
	@Override
	public ResourceRoleQueryCondition setGmtModifyEnd(Date to){
		put("gmtModifyEnd", DateTools.getDayEnd(to));
		return this;
	}
	
	@Override
	public ResourceRoleQueryCondition setStart(int start){
		put("start", start);
		return this;
	}
	
	@Override
	public ResourceRoleQueryCondition setPageSize(int pageSize){
		put("pageSize", pageSize);
		return this;
	}
}
