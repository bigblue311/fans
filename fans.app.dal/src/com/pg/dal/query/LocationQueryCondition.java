package com.pg.dal.query;

import java.util.Date;

import com.victor.framework.common.tools.DateTools;
import com.victor.framework.dal.basic.QueryCondition;

public class LocationQueryCondition extends QueryCondition {
	
	@Override
	public LocationQueryCondition setGmtModifyStart(Date from){
		put("gmtModifyStart", DateTools.getDayBegin(from));
		return this;
	}
	
	@Override
	public LocationQueryCondition setGmtModifyEnd(Date to){
		put("gmtModifyEnd", DateTools.getDayEnd(to));
		return this;
	}
	
	@Override
	public LocationQueryCondition setStart(int start){
		put("start", start);
		return this;
	}
	
	@Override
	public LocationQueryCondition setPageSize(int pageSize){
		put("pageSize", pageSize);
		return this;
	}
}
