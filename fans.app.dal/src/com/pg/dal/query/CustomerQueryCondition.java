package com.pg.dal.query;

import java.util.Date;

import com.victor.framework.common.tools.DateTools;
import com.victor.framework.dal.basic.QueryCondition;

public class CustomerQueryCondition extends QueryCondition {
	
	public CustomerQueryCondition setId(Long id) {
		put("id",id);
		return this;
	}
	
	public Long getId(){
		return getLong("id");
	}
	
	public CustomerQueryCondition setName(String name) {
		put("name",name);
		return this;
	}
	
	public String getName(){
		return getString("name");
	}
	
	public CustomerQueryCondition setMobile(String mobile) {
		put("mobile",mobile);
		return this;
	}
	
	public String getMobile(){
		return getString("mobile");
	}
	
	public CustomerQueryCondition setEmployeeId(Long employeeId) {
		put("employeeId",employeeId);
		return this;
	}
	
	public Long getEmployeeId(){
		return getLong("employeeId");
	}
	
	public CustomerQueryCondition setRecommender(String recommender) {
        put("recommender",recommender);
        return this;
    }
    
    public String getRecommender(){
        return getString("recommender");
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

	public CustomerQueryCondition setGmtCreateStart(Date from){
		put("gmtCreateStart", DateTools.getDayBegin(from));
		return this;
	}
	
	public Date getGmtCreateStart(){
		return getDate("gmtCreateStart");
	}
	
	public CustomerQueryCondition setGmtCreateEnd(Date to){
		put("gmtCreateEnd", DateTools.getDayEnd(to));
		return this;
	}
	
	public Date getGmtCreateEnd(){
		return getDate("gmtCreateEnd");
	}
	
	@Override
	public CustomerQueryCondition setGmtModifyStart(Date from){
		put("gmtModifyStart", DateTools.getDayBegin(from));
		return this;
	}
	
	@Override
	public CustomerQueryCondition setGmtModifyEnd(Date to){
		put("gmtModifyEnd", DateTools.getDayEnd(to));
		return this;
	}
	
	@Override
	public CustomerQueryCondition setStart(int start){
		put("start", start);
		return this;
	}
	
	@Override
	public CustomerQueryCondition setPageSize(int pageSize){
		put("pageSize", pageSize);
		return this;
	}
}
