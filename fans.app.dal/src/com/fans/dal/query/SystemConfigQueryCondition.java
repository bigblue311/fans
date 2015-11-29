package com.fans.dal.query;

import java.util.Date;

import com.victor.framework.common.tools.DateTools;
import com.victor.framework.dal.basic.QueryCondition;

public class SystemConfigQueryCondition extends QueryCondition {
	
	public SystemConfigQueryCondition setKey(String key) {
		put("configKey",key);
		return this;
	}
	
	public String getKey(){
		return getString("configKey");
	}
	
	@Override
	public SystemConfigQueryCondition setGmtModifyStart(Date from){
		put("gmtModifyStart", DateTools.getDayBegin(from));
		return this;
	}
	
	@Override
	public SystemConfigQueryCondition setGmtModifyEnd(Date to){
		put("gmtModifyEnd", DateTools.getDayEnd(to));
		return this;
	}
	
	@Override
	public SystemConfigQueryCondition setStart(int start){
		put("start", start);
		return this;
	}
	
	@Override
	public SystemConfigQueryCondition setPageSize(int pageSize){
		put("pageSize", pageSize);
		return this;
	}
}
