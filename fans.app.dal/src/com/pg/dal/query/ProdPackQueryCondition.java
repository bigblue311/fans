package com.pg.dal.query;

import java.util.Date;

import com.victor.framework.common.tools.DateTools;
import com.victor.framework.dal.basic.QueryCondition;

public class ProdPackQueryCondition extends QueryCondition {
	
	public ProdPackQueryCondition setPackageId(Long packageId) {
		put("packageId",packageId);
		return this;
	}
	
	public Long getPackageId(){
		return getLong("packageId");
	}
	
	public ProdPackQueryCondition setProductId(Long productId) {
		put("productId",productId);
		return this;
	}
	
	public Long getProductId(){
		return getLong("productId");
	}
	
	@Override
	public ProdPackQueryCondition setGmtModifyStart(Date from){
		put("gmtModifyStart", DateTools.getDayBegin(from));
		return this;
	}
	
	@Override
	public ProdPackQueryCondition setGmtModifyEnd(Date to){
		put("gmtModifyEnd", DateTools.getDayEnd(to));
		return this;
	}
	
	@Override
	public ProdPackQueryCondition setStart(int start){
		put("start", start);
		return this;
	}
	
	@Override
	public ProdPackQueryCondition setPageSize(int pageSize){
		put("pageSize", pageSize);
		return this;
	}
}
