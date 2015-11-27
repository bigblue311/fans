package com.pg.dal.query;

import java.util.Date;

import com.victor.framework.common.tools.DateTools;
import com.victor.framework.dal.basic.QueryCondition;

public class PurchaseItemQueryCondition extends QueryCondition {
	
	public PurchaseItemQueryCondition setName(String name) {
		put("name",name);
		return this;
	}
	
	public String getName(){
		return getString("name");
	}
	
	public PurchaseItemQueryCondition setTitle(String title) {
		put("title",title);
		return this;
	}
	
	public String getTitle(){
		return getString("title");
	}
	
	public PurchaseItemQueryCondition setPurchaseId(Long purchaseId) {
		put("purchaseId",purchaseId);
		return this;
	}
	
	public Long getPurchaseId(){
		return getLong("purchaseId");
	}
	
	public PurchaseItemQueryCondition setBrandId(Long brandId) {
		put("brandId",brandId);
		return this;
	}
	
	public Long getBrandId(){
		return getLong("brandId");
	}
	
	public PurchaseItemQueryCondition setCategoryId(Long categoryId) {
		put("categoryId",categoryId);
		return this;
	}
	
	public Long getCategoryId(){
		return getLong("categoryId");
	}
	
	@Override
	public PurchaseItemQueryCondition setGmtModifyStart(Date from){
		put("gmtModifyStart", DateTools.getDayBegin(from));
		return this;
	}
	
	@Override
	public PurchaseItemQueryCondition setGmtModifyEnd(Date to){
		put("gmtModifyEnd", DateTools.getDayEnd(to));
		return this;
	}
	
	@Override
	public PurchaseItemQueryCondition setStart(int start){
		put("start", start);
		return this;
	}
	
	@Override
	public PurchaseItemQueryCondition setPageSize(int pageSize){
		put("pageSize", pageSize);
		return this;
	}
}
