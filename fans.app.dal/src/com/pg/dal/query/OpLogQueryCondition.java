package com.pg.dal.query;

import java.util.Date;

import com.victor.framework.common.tools.DateTools;
import com.victor.framework.dal.basic.QueryCondition;

public class OpLogQueryCondition extends QueryCondition {
	
	public OpLogQueryCondition setEmployeeId(Long employeeId) {
		put("employeeId",employeeId);
		return this;
	}
	
	public Long getEmployeeId(){
		return getLong("employeeId");
	}
	
	public OpLogQueryCondition setOrderId(Long orderId) {
		put("orderId",orderId);
		return this;
	}
	
	public Long getOrderId(){
		return getLong("orderId");
	}
	
	@Override
	public OpLogQueryCondition setGmtModifyStart(Date from){
		put("gmtModifyStart", DateTools.getDayBegin(from));
		return this;
	}
	
	@Override
	public OpLogQueryCondition setGmtModifyEnd(Date to){
		put("gmtModifyEnd", DateTools.getDayEnd(to));
		return this;
	}
	
	@Override
	public OpLogQueryCondition setStart(int start){
		put("start", start);
		return this;
	}
	
	@Override
	public OpLogQueryCondition setPageSize(int pageSize){
		put("pageSize", pageSize);
		return this;
	}
}
