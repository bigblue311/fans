package com.pg.dal.query;

import java.util.Date;

import com.victor.framework.common.tools.DateTools;
import com.victor.framework.dal.basic.QueryCondition;

public class NewsQueryCondition extends QueryCondition {
	
	@Override
	public NewsQueryCondition setGmtModifyStart(Date from){
		put("gmtModifyStart", DateTools.getDayBegin(from));
		return this;
	}
	
	@Override
	public NewsQueryCondition setGmtModifyEnd(Date to){
		put("gmtModifyEnd", DateTools.getDayEnd(to));
		return this;
	}
	
	@Override
	public NewsQueryCondition setStart(int start){
		put("start", start);
		return this;
	}
	
	@Override
	public NewsQueryCondition setPageSize(int pageSize){
		put("pageSize", pageSize);
		return this;
	}
}
