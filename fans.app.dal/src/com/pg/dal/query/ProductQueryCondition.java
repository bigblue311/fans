package com.pg.dal.query;

import java.util.Date;

import com.victor.framework.common.tools.DateTools;
import com.victor.framework.dal.basic.QueryCondition;

public class ProductQueryCondition extends QueryCondition {
	
	public ProductQueryCondition setName(String name) {
		put("name",name);
		return this;
	}
	
	public String getName(){
		return getString("name");
	}
	
	public ProductQueryCondition setTitle(String title) {
		put("title",title);
		return this;
	}
	
	public String getTitle(){
		return getString("title");
	}
	
	public ProductQueryCondition setEnable(String enable){
		put("enable",enable);
		return this;
	}
	
	public ProductQueryCondition setPackageId(Long packageId) {
		put("packageId",packageId);
		return this;
	}
	
	public Long getPackageId(){
		return getLong("packageId");
	}
	
	public ProductQueryCondition setNotInPackageId(Long packageId) {
		put("notInPackageId",packageId);
		return this;
	}
	
	public Long getNotInPackageId(){
		return getLong("notInPackageId");
	}
	
	public ProductQueryCondition setPublishId(Long publishId) {
		put("publishId",publishId);
		return this;
	}
	
	public Long getPublishId(){
		return getLong("publishId");
	}
	
	public ProductQueryCondition setBrandId(Long brandId) {
		put("brandId",brandId);
		return this;
	}
	
	public Long getBrandId(){
		return getLong("brandId");
	}
	
	public ProductQueryCondition setCategoryId(Long categoryId) {
		put("categoryId",categoryId);
		return this;
	}
	
	public Long getCategoryId(){
		return getLong("categoryId");
	}
	
	@Override
	public ProductQueryCondition setGmtModifyStart(Date from){
		put("gmtModifyStart", DateTools.getDayBegin(from));
		return this;
	}
	
	@Override
	public ProductQueryCondition setGmtModifyEnd(Date to){
		put("gmtModifyEnd", DateTools.getDayEnd(to));
		return this;
	}
	
	@Override
	public ProductQueryCondition setStart(int start){
		put("start", start);
		return this;
	}
	
	@Override
	public ProductQueryCondition setPageSize(int pageSize){
		put("pageSize", pageSize);
		return this;
	}
}
