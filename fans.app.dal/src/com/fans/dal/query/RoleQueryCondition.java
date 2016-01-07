package com.fans.dal.query;

import java.util.Date;

import com.victor.framework.common.tools.DateTools;
import com.victor.framework.dal.basic.QueryCondition;

public class RoleQueryCondition extends QueryCondition {
	
	@Override
	public RoleQueryCondition setGmtModifyStart(Date from){
		put("gmtModifyStart", DateTools.getDayBegin(from));
		return this;
	}
	
	@Override
	public RoleQueryCondition setGmtModifyEnd(Date to){
		put("gmtModifyEnd", DateTools.getDayEnd(to));
		return this;
	}
	
	@Override
	public RoleQueryCondition setStart(int start){
		put("start", start);
		return this;
	}
	
	@Override
	public RoleQueryCondition setPageSize(int pageSize){
		put("pageSize", pageSize);
		return this;
	}
}
