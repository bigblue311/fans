package com.fans.dal.query;

import java.io.Serializable;
import java.util.Date;

import com.victor.framework.common.tools.DateTools;
import com.victor.framework.dal.basic.QueryCondition;

public class CoinsQueryCondition extends QueryCondition implements Serializable{
	
	/**
     * 
     */
    private static final long serialVersionUID = 4331737247122405493L;

    public CoinsQueryCondition setId(Long id) {
		put("id",id);
		return this;
	}
	
	public Long getId(){
		return getLong("id");
	}
	
	public CoinsQueryCondition setUserId(Long userId) {
		put("userId",userId);
		return this;
	}
	
	public Long getUserId(){
		return getLong("userId");
	}
	
	public CoinsQueryCondition setOpenId(String openId) {
		put("openId",openId);
		return this;
	}
	
	public String getOpenId(){
		return getString("openId");
	}
	
	public CoinsQueryCondition setType(Integer type) {
		put("type",type);
		return this;
	}
	
	public Integer getType(){
		return getInteger("type");
	}
	
	@Override
	public CoinsQueryCondition setGmtModifyStart(Date from){
		put("gmtModifyStart", DateTools.getDayBegin(from));
		return this;
	}
	
	@Override
	public CoinsQueryCondition setGmtModifyEnd(Date to){
		put("gmtModifyEnd", DateTools.getDayEnd(to));
		return this;
	}
	
	@Override
	public CoinsQueryCondition setStart(int start){
		put("start", start);
		return this;
	}
	
	@Override
	public CoinsQueryCondition setPageSize(int pageSize){
		put("pageSize", pageSize);
		return this;
	}
}
