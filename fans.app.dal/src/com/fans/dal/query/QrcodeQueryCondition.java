package com.fans.dal.query;

import java.util.Date;

import com.victor.framework.common.tools.DateTools;
import com.victor.framework.dal.basic.QueryCondition;

public class QrcodeQueryCondition extends QueryCondition {
	
	public QrcodeQueryCondition setUserId(Long userId) {
		put("userId",userId);
		return this;
	}
	
	public Long getUserId(){
		return getLong("userId");
	}
	
	public QrcodeQueryCondition setSkvId(String skvId) {
		put("skvId",skvId);
		return this;
	}
	
	public String getSkvId(){
		return getString("skvId");
	}
	
	public QrcodeQueryCondition setDomain(String domain) {
		put("domain", domain);
		return this;
	}
	
	public String getDomain(){
		return getString("domain");
	}
	
	@Override
	public QrcodeQueryCondition setGmtModifyStart(Date from){
		put("gmtModifyStart", DateTools.getDayBegin(from));
		return this;
	}
	
	@Override
	public QrcodeQueryCondition setGmtModifyEnd(Date to){
		put("gmtModifyEnd", DateTools.getDayEnd(to));
		return this;
	}
	
	@Override
	public QrcodeQueryCondition setStart(int start){
		put("start", start);
		return this;
	}
	
	@Override
	public QrcodeQueryCondition setPageSize(int pageSize){
		put("pageSize", pageSize);
		return this;
	}
}
