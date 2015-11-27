package com.pg.dal.query;

import java.util.Date;

import com.victor.framework.common.tools.DateTools;
import com.victor.framework.dal.basic.QueryCondition;

public class WarehouseQueryCondition extends QueryCondition {
	
	public WarehouseQueryCondition setName(String name) {
		put("name",name);
		return this;
	}
	
	public String getName(){
		return getString("name");
	}
	
	public WarehouseQueryCondition setAddress(String address){
		put("address",address);
		return this;
	}
	
	public String getAddress(){
		return getString("address");
	}
	
	public WarehouseQueryCondition setKeeper(String keeper){
		put("keeper",keeper);
		return this;
	}
	
	public String getKeeper(){
		return getString("keeper");
	}
	
	public WarehouseQueryCondition setMobile(String mobile){
		put("mobile",mobile);
		return this;
	}
	
	public String getMobile(){
		return getString("mobile");
	}
	
	public WarehouseQueryCondition setPhone(String phone){
		put("phone",phone);
		return this;
	}
	
	public String getPhone(){
		return getString("phone");
	}
	
	public WarehouseQueryCondition setCustomerId(Long customerId) {
		put("customerId",customerId);
		return this;
	}
	
	public Long getCustomerId(){
		return getLong("customerId");
	}
	
	public WarehouseQueryCondition setSystem(Boolean system) {
		put("system",system);
		return this;
	}
	
	public Boolean getSystem(){
		return getBoolean("system");
	}
	
	public WarehouseQueryCondition setProvince(String province){
		put("province",province);
		return this;
	}
	
	public String getProvince(){
		return getString("province");
	}
	
	public WarehouseQueryCondition setCity(String city){
		put("city",city);
		return this;
	}
	
	public String getCity(){
		return getString("city");
	}
	
	public WarehouseQueryCondition setTown(String town){
		put("town",town);
		return this;
	}
	
	public String getTown(){
		return getString("town");
	}
	
	@Override
	public WarehouseQueryCondition setGmtModifyStart(Date from){
		put("gmtModifyStart", DateTools.getDayBegin(from));
		return this;
	}
	
	@Override
	public WarehouseQueryCondition setGmtModifyEnd(Date to){
		put("gmtModifyEnd", DateTools.getDayEnd(to));
		return this;
	}
	
	@Override
	public WarehouseQueryCondition setStart(int start){
		put("start", start);
		return this;
	}
	
	@Override
	public WarehouseQueryCondition setPageSize(int pageSize){
		put("pageSize", pageSize);
		return this;
	}
}
