package com.pg.dal.query;

import java.util.Date;

import com.victor.framework.common.tools.DateTools;
import com.victor.framework.dal.basic.QueryCondition;

public class CategoryQueryCondition extends QueryCondition {
	
	public CategoryQueryCondition setName(String name) {
		put("name",name);
		return this;
	}
	
	public String getName(){
		return getString("name");
	}
	
	public CategoryQueryCondition setParentId(Long parentId) {
		put("parentId",parentId);
		return this;
	}
	
	public Long getParentId(){
		return getLong("parentId");
	}
	
	@Override
	public CategoryQueryCondition setGmtModifyStart(Date from){
		put("gmtModifyStart", DateTools.getDayBegin(from));
		return this;
	}
	
	@Override
	public CategoryQueryCondition setGmtModifyEnd(Date to){
		put("gmtModifyEnd", DateTools.getDayEnd(to));
		return this;
	}
	
	@Override
	public CategoryQueryCondition setStart(int start){
		put("start", start);
		return this;
	}
	
	@Override
	public CategoryQueryCondition setPageSize(int pageSize){
		put("pageSize", pageSize);
		return this;
	}
}
