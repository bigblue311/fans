package com.pg.dal.query;

import java.util.Date;

import com.victor.framework.common.tools.DateTools;
import com.victor.framework.dal.basic.QueryCondition;

public class PurchaseQueryCondition extends QueryCondition {
	
	public PurchaseQueryCondition setName(String name){
		put("name", name);
		return this;
	}
	
	public String getName(){
		return getString("name");
	}
	
	public PurchaseQueryCondition setCustomerId(Long customerId) {
		put("customerId",customerId);
		return this;
	}
	
	public Long getCutomerId(){
		return getLong("customerId");
	}
	
	public PurchaseQueryCondition setCustomerName(Long customerName) {
		put("customerName",customerName);
		return this;
	}
	
	public Long getCutomerName(){
		return getLong("customerName");
	}
	
	public PurchaseQueryCondition setCustomerMobile(Long customerMobile) {
		put("customerMobile",customerMobile);
		return this;
	}
	
	public Long getCutomerMobile(){
		return getLong("customerMobile");
	}
	
	public PurchaseQueryCondition setAddressFrom(String addressFrom) {
		put("addressFrom",addressFrom);
		return this;
	}
	
	public String getAddressFrom(){
		return getString("addressFrom");
	}
	
	public PurchaseQueryCondition setAddressTo(String addressTo) {
		put("addressTo",addressTo);
		return this;
	}
	
	public String getAddressTo(){
		return getString("addressTo");
	}
	
	public PurchaseQueryCondition setKeeper(String keeper) {
		put("keeper",keeper);
		return this;
	}
	
	public String getKeeper(){
		return getString("keeper");
	}
	
	public PurchaseQueryCondition setPhone(String phone) {
		put("phone",phone);
		return this;
	}
	
	public String getPhone(){
		return getString("phone");
	}
	
	public PurchaseQueryCondition setMobile(String mobile) {
		put("mobile",mobile);
		return this;
	}
	
	public String getMobile(){
		return getString("mobile");
	}
	
	public PurchaseQueryCondition setTransportFeeStart(Double transportFeeStart) {
		put("transportFeeStart",transportFeeStart);
		return this;
	}
	
	public Double setTransportFeeStart(){
		return getDouble("transportFeeStart");
	}
	
	public PurchaseQueryCondition setTransportFeeEnd(Double transportFeeEnd) {
		put("transportFeeEnd",transportFeeEnd);
		return this;
	}
	
	public Double getTransportFeeEnd(){
		return getDouble("transportFeeEnd");
	}
	
	public PurchaseQueryCondition setTransportCode(Double transportCode) {
		put("transportCode",transportCode);
		return this;
	}
	
	public String getTransportCode(){
		return getString("transportCode");
	}
	
	public PurchaseQueryCondition setPublishId(Long publishId) {
		put("publishId",publishId);
		return this;
	}
	
	public Long getPublishId(){
		return getLong("publishId");
	}
	
	public PurchaseQueryCondition setPackageId(Long packageId) {
		put("packageId",packageId);
		return this;
	}
	
	public Long getPackageId(){
		return getLong("packageId");
	}
	
	public PurchaseQueryCondition setOrderId(Long orderId) {
		put("orderId",orderId);
		return this;
	}
	
	public Long getOrderId(){
		return getLong("orderId");
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

	public PurchaseQueryCondition setGmtCreateStart(Date from){
		put("gmtCreateStart", DateTools.getDayBegin(from));
		return this;
	}
	
	public Date getGmtCreateStart(){
		return getDate("gmtCreateStart");
	}
	
	public PurchaseQueryCondition setGmtCreateEnd(Date to){
		put("gmtCreateEnd", DateTools.getDayEnd(to));
		return this;
	}
	
	public Date getGmtCreateEnd(){
		return getDate("gmtCreateEnd");
	}
	
	@Override
	public PurchaseQueryCondition setGmtModifyStart(Date from){
		put("gmtModifyStart", DateTools.getDayBegin(from));
		return this;
	}
	
	@Override
	public PurchaseQueryCondition setGmtModifyEnd(Date to){
		put("gmtModifyEnd", DateTools.getDayEnd(to));
		return this;
	}
	
	@Override
	public PurchaseQueryCondition setStart(int start){
		put("start", start);
		return this;
	}
	
	@Override
	public PurchaseQueryCondition setPageSize(int pageSize){
		put("pageSize", pageSize);
		return this;
	}
}
