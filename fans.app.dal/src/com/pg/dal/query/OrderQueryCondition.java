package com.pg.dal.query;

import java.util.Date;

import com.victor.framework.common.tools.DateTools;
import com.victor.framework.dal.basic.QueryCondition;

public class OrderQueryCondition extends QueryCondition {
	
	public OrderQueryCondition setCustomerId(Long customerId) {
		put("customerId",customerId);
		return this;
	}
	
	public Long getCustomerId(){
		return getLong("customerId");
	}
	
	public OrderQueryCondition setCustomerName(Long customerName) {
		put("customerName",customerName);
		return this;
	}
	
	public Long getCutomerName(){
		return getLong("customerName");
	}
	
	public OrderQueryCondition setCustomerMobile(Long customerMobile) {
		put("customerMobile",customerMobile);
		return this;
	}
	
	public Long getCutomerMobile(){
		return getLong("customerMobile");
	}
	
	public OrderQueryCondition setDepositStart(Double depositStart) {
		put("depositStart",depositStart);
		return this;
	}
	
	public Double getDepositStart(){
		return getDouble("depositStart");
	}
	
	public OrderQueryCondition setDepositEnd(Double depositEnd) {
		put("depositEnd",depositEnd);
		return this;
	}
	
	public Double getDepositEnd(){
		return getDouble("depositEnd");
	}
	
	public OrderQueryCondition setTotalPriceStart(Double totalPriceStart) {
		put("totalPriceStart",totalPriceStart);
		return this;
	}
	
	public Double getTotalPriceStart(){
		return getDouble("totalPriceStart");
	}
	
	public OrderQueryCondition setTotalPriceEnd(Double totalPriceEnd) {
		put("totalPriceEnd",totalPriceEnd);
		return this;
	}
	
	public Double getTotalPriceEnd(){
		return getDouble("totalPriceEnd");
	}
	
	public OrderQueryCondition setTransportFeeStart(Double transportFeeStart) {
		put("transportFeeStart",transportFeeStart);
		return this;
	}
	
	public Double setTransportFeeStart(){
		return getDouble("transportFeeStart");
	}
	
	public OrderQueryCondition setTransportFeeEnd(Double transportFeeEnd) {
		put("transportFeeEnd",transportFeeEnd);
		return this;
	}
	
	public Double getTransportFeeEnd(){
		return getDouble("transportFeeEnd");
	}
	
	public OrderQueryCondition setStatus(String status) {
		put("status",status);
		return this;
	}
	
	public String getStatus(){
		return getString("status");
	}
	
	public OrderQueryCondition status(String... status) {
		put("statuses",status);
		return this;
	}
	
	public void setCreateStart(String createStart){
		put("createStart",createStart);
		setGmtCreateStart(StringToDate(createStart));
	}
	
	public String getCreateStart(){
		return getString("createStart");
	}
	
	public void setCreateEnd(String createEnd){
		put("createEnd",createEnd);
		setGmtCreateEnd(StringToDate(createEnd));
	}
	
	public String getCreateEnd(){
		return getString("createEnd");
	}

	public OrderQueryCondition setGmtCreateStart(Date from){
		put("gmtCreateStart", DateTools.getDayBegin(from));
		return this;
	}
	
	public Date getGmtCreateStart(){
		return getDate("gmtCreateStart");
	}
	
	public OrderQueryCondition setGmtCreateEnd(Date to){
		put("gmtCreateEnd", DateTools.getDayEnd(to));
		return this;
	}
	
	public Date getGmtCreateEnd(){
		return getDate("gmtCreateEnd");
	}
	
	@Override
	public OrderQueryCondition setGmtModifyStart(Date from){
		put("gmtModifyStart", DateTools.getDayBegin(from));
		return this;
	}
	
	@Override
	public OrderQueryCondition setGmtModifyEnd(Date to){
		put("gmtModifyEnd", DateTools.getDayEnd(to));
		return this;
	}
	
	@Override
	public OrderQueryCondition setStart(int start){
		put("start", start);
		return this;
	}
	
	@Override
	public OrderQueryCondition setPageSize(int pageSize){
		put("pageSize", pageSize);
		return this;
	}
}
