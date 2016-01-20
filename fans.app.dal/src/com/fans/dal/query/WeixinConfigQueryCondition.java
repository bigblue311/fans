package com.fans.dal.query;

import java.util.Date;

import com.victor.framework.common.tools.DateTools;
import com.victor.framework.dal.basic.QueryCondition;

public class WeixinConfigQueryCondition extends QueryCondition {
	
	public WeixinConfigQueryCondition setDomain(String domain) {
		put("domain",domain);
		return this;
	}
	
	public String getDomain(){
		return getString("domain");
	}
	
	@Override
	public WeixinConfigQueryCondition setGmtModifyStart(Date from){
		put("gmtModifyStart", DateTools.getDayBegin(from));
		return this;
	}
	
	@Override
	public WeixinConfigQueryCondition setGmtModifyEnd(Date to){
		put("gmtModifyEnd", DateTools.getDayEnd(to));
		return this;
	}
	
	@Override
	public WeixinConfigQueryCondition setStart(int start){
		put("start", start);
		return this;
	}
	
	@Override
	public WeixinConfigQueryCondition setPageSize(int pageSize){
		put("pageSize", pageSize);
		return this;
	}
}
