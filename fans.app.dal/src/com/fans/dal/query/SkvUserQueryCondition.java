package com.fans.dal.query;

import java.io.Serializable;
import java.util.Date;

import com.victor.framework.common.tools.DateTools;
import com.victor.framework.dal.basic.QueryCondition;

public class SkvUserQueryCondition extends QueryCondition implements Serializable{
	
	/**
     * 
     */
    private static final long serialVersionUID = 4331737247122405493L;

    public SkvUserQueryCondition setId(Long id) {
		put("id",id);
		return this;
	}
	
	public Long getId(){
		return getLong("id");
	}
	
	@Override
	public SkvUserQueryCondition setGmtModifyStart(Date from){
		put("gmtModifyStart", DateTools.getDayBegin(from));
		return this;
	}
	
	@Override
	public SkvUserQueryCondition setGmtModifyEnd(Date to){
		put("gmtModifyEnd", DateTools.getDayEnd(to));
		return this;
	}
	
	@Override
	public SkvUserQueryCondition setStart(int start){
		put("start", start);
		return this;
	}
	
	@Override
	public SkvUserQueryCondition setPageSize(int pageSize){
		put("pageSize", pageSize);
		return this;
	}
}
