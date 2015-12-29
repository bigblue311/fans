package com.fans.dal.query;

import java.io.Serializable;
import java.util.Date;

import com.victor.framework.common.tools.DateTools;
import com.victor.framework.dal.basic.QueryCondition;

public class TopListQueryCondition extends QueryCondition implements Serializable{
	
	/**
     * 
     */
    private static final long serialVersionUID = 4331737247122405493L;

	public TopListQueryCondition setUserId(Long userId) {
		put("userId",userId);
		return this;
	}
	
	public Long getUserId(){
		return getLong("userId");
	}
	
	public TopListQueryCondition setOpenId(String openId) {
		put("openId",openId);
		return this;
	}
	
	public String getOpenId(){
		return getString("openId");
	}
	
	public TopListQueryCondition setValid(Integer valid) {
		put("valid", valid);
		return this;
	}
	
	public Integer getValid(){
		return getInteger("valid");
	}
	
	public TopListQueryCondition setPosition(Integer position) {
		put("position",position);
		return this;
	}
	
	public Integer getPosition(){
		return getInteger("position");
	}
	
	@Override
	public TopListQueryCondition setGmtModifyStart(Date from){
		put("gmtModifyStart", DateTools.getDayBegin(from));
		return this;
	}
	
	@Override
	public TopListQueryCondition setGmtModifyEnd(Date to){
		put("gmtModifyEnd", DateTools.getDayEnd(to));
		return this;
	}
	
	@Override
	public TopListQueryCondition setStart(int start){
		put("start", start);
		return this;
	}
	
	@Override
	public TopListQueryCondition setPageSize(int pageSize){
		put("pageSize", pageSize);
		return this;
	}
}
