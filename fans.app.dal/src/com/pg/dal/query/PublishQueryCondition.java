package com.pg.dal.query;

import java.util.Date;

import com.victor.framework.common.tools.DateTools;
import com.victor.framework.dal.basic.QueryCondition;

public class PublishQueryCondition extends QueryCondition {
	
	public PublishQueryCondition setWarehouseId(Long warehouseId) {
		put("warehouseId",warehouseId);
		return this;
	}
	
	public Long getWarehouseId(){
		return getLong("warehouseId");
	}
	
	public PublishQueryCondition setProvince(String province){
		put("province",province);
		return this;
	}
	
	public String getProvince(){
		return getString("province");
	}
	
	public PublishQueryCondition setCity(String city){
		put("city",city);
		return this;
	}
	
	public String getCity(){
		return getString("city");
	}
	
	public PublishQueryCondition setTown(String town){
		put("town",town);
		return this;
	}
	
	public String getTown(){
		return getString("town");
	}
	
	public PublishQueryCondition setEnable(String enable){
		put("enable",enable);
		return this;
	}
	
	public PublishQueryCondition setPackageId(Long packageId) {
		put("packageId",packageId);
		return this;
	}
	
	public Long getPackageId(){
		return getLong("packageId");
	}
	
	public PublishQueryCondition setName(String name){
		put("name", name);
		return this;
	}
	
	public String getName(){
		return getString("name");
	}
	
	public PublishQueryCondition setValid(Boolean valid) {
		put("valid",valid);
		return this;
	}
	
	public Boolean getValid(){
		return getBoolean("valid");
	}
	
	public PublishQueryCondition setOrderBy(String orderBy){
		put("orderBy",orderBy);
		return this;
	}
	
	public String getOrderBy(){
		return getString("orderBy");
	}
	
	public PublishQueryCondition setOrder(String order){
		put("order",order);
		return this;
	}
	
	public String getOrder(){
		return getString("order");
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

	public PublishQueryCondition setGmtCreateStart(Date from){
		put("gmtCreateStart", DateTools.getDayBegin(from));
		return this;
	}
	
	public Date getGmtCreateStart(){
		return getDate("gmtCreateStart");
	}
	
	public PublishQueryCondition setGmtCreateEnd(Date to){
		put("gmtCreateEnd", DateTools.getDayEnd(to));
		return this;
	}
	
	public Date getGmtCreateEnd(){
		return getDate("gmtCreateEnd");
	}
	
	@Override
	public PublishQueryCondition setGmtModifyStart(Date from){
		put("gmtModifyStart", DateTools.getDayBegin(from));
		return this;
	}
	
	@Override
	public PublishQueryCondition setGmtModifyEnd(Date to){
		put("gmtModifyEnd", DateTools.getDayEnd(to));
		return this;
	}
	
	@Override
	public PublishQueryCondition setStart(int start){
		put("start", start);
		return this;
	}
	
	@Override
	public PublishQueryCondition setPageSize(int pageSize){
		put("pageSize", pageSize);
		return this;
	}
}
