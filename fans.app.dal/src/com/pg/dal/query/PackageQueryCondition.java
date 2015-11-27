package com.pg.dal.query;

import java.util.Date;

import com.victor.framework.common.tools.DateTools;
import com.victor.framework.dal.basic.QueryCondition;

public class PackageQueryCondition extends QueryCondition {
	
	public PackageQueryCondition setName(String name) {
		put("name",name);
		return this;
	}
	
	public String getName(){
		return getString("name");
	}
	
	public PackageQueryCondition setTitle(String title) {
		put("title",title);
		return this;
	}
	
	public String getTitle(){
		return getString("title");
	}
	
	@Override
	public PackageQueryCondition setGmtModifyStart(Date from){
		put("gmtModifyStart", DateTools.getDayBegin(from));
		return this;
	}
	
	@Override
	public PackageQueryCondition setGmtModifyEnd(Date to){
		put("gmtModifyEnd", DateTools.getDayEnd(to));
		return this;
	}
	
	@Override
	public PackageQueryCondition setStart(int start){
		put("start", start);
		return this;
	}
	
	@Override
	public PackageQueryCondition setPageSize(int pageSize){
		put("pageSize", pageSize);
		return this;
	}
}
