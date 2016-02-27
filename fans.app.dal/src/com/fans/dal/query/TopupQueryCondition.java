package com.fans.dal.query;

import java.io.Serializable;
import java.util.Date;

import com.victor.framework.common.tools.DateTools;
import com.victor.framework.dal.basic.QueryCondition;

public class TopupQueryCondition extends QueryCondition implements Serializable{
	
	/**
     * 
     */
    private static final long serialVersionUID = 4331737247122405493L;
    
    public TopupQueryCondition setId(Long id) {
		put("id",id);
		return this;
	}
	
	public Long getId(){
		return getLong("id");
	}
	
	public TopupQueryCondition setUserName(String userName) {
        put("userName",userName);
        return this;
    }
    
    public String getUserName(){
        return getString("userName");
    }
	
	public TopupQueryCondition setUserId(Long userId) {
		put("userId",userId);
		return this;
	}
	
	public Long getUserId(){
		return getLong("userId");
	}
	
	public TopupQueryCondition setOpenId(String openId) {
		put("openId",openId);
		return this;
	}
	
	public String getOpenId(){
		return getString("openId");
	}
	
	public TopupQueryCondition setStatus(Integer status) {
		put("status",status);
		return this;
	}
	
	public Integer getStatus(){
		return getInteger("status");
	}
	
	public TopupQueryCondition setType(Integer type) {
		put("type",type);
		return this;
	}
	
	public Integer getType(){
		return getInteger("type");
	}
	
	public TopupQueryCondition setOperator(String operator) {
        put("operator",operator);
        return this;
    }
    
    public String getOperator(){
        return getString("operator");
    }
	
	@Override
	public TopupQueryCondition setGmtModifyStart(Date from){
		put("gmtModifyStart", DateTools.getDayBegin(from));
		return this;
	}
	
	@Override
	public TopupQueryCondition setGmtModifyEnd(Date to){
		put("gmtModifyEnd", DateTools.getDayEnd(to));
		return this;
	}
	
	@Override
	public TopupQueryCondition setStart(int start){
		put("start", start);
		return this;
	}
	
	@Override
	public TopupQueryCondition setPageSize(int pageSize){
		put("pageSize", pageSize);
		return this;
	}
}
