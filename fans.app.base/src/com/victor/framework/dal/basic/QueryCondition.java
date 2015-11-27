package com.victor.framework.dal.basic;

import java.util.Date;
import java.util.Map;

import com.google.common.collect.Maps;
import com.victor.framework.common.tools.DateTools;
import com.victor.framework.common.tools.StringTools;

public abstract class QueryCondition {
	private Map<String,Object> queryMap = Maps.newHashMap();
	
	public QueryCondition(){
		queryMap.put("start", 0);
		queryMap.put("pageSize", 20);
		queryMap.put("enable", "0");
	}
	
	public final void put(String key, Object value) {
		queryMap.put(key, value);
	}

	public Map<String, Object> getQueryMap() {
		return queryMap;
	}
	
	public void setQueryMap(Map<String,Object> queryMap){
		this.queryMap = queryMap;
	}
	
	public void clearEnable(){
		this.queryMap.remove("enable");
	}
	
	public void setEnable(){
		queryMap.put("enable", "0");
	}
	
	public String getEnable(){
		return getString("enable");
	}
	
	public void setModifyStart(String modifyStart){
		put("modifyStart",modifyStart);
		setGmtModifyStart(StringToDate(modifyStart));
	}
	
	public String getModifyStart(){
		return getString("modifyStart");
	}
	
	public void setModifyEnd(String modifyEnd){
		put("modifyEnd",modifyEnd);
		setGmtModifyEnd(StringToDate(modifyEnd));
	}
	
	public String getModifyEnd(){
		return getString("modifyEnd");
	}

	public abstract QueryCondition setGmtModifyStart(Date from);
	
	public Date getGmtModifyStart(){
		return getDate("gmtModifyStart");
	}
	
	public abstract QueryCondition setGmtModifyEnd(Date to);
	
	public Date getGmtModifyEnd(){
		return getDate("gmtModifyEnd");
	}
	
	public abstract QueryCondition setStart(int start);
	
	public Integer getStart(){
		return getInteger("start");
	}
	
	public abstract QueryCondition setPageSize(int pageSize);
	
	public Integer getPageSize(){
		return getInteger("pageSize",20);
	}
	
	public void setPage(int page){
		if(page == 0) {
			page = 1;
		}
		int pageSize = getInteger("pageSize",20);
		setStart((page-1)*pageSize);
		put("page",page);
	}
	
	public Integer getPage(){
		return getInteger("page",1);
	}
	
	public Double getDouble(String key){
		Object obj = queryMap.get(key);
		if(obj == null) {
			return null;
		} else {
			try {
				return Double.parseDouble(obj.toString());
			} catch (Exception e) {
				return null;
			}
		}
	}
	
	public Double getDouble(String key,Double defaultValue){
		Double value = getDouble(key);
		return value == null ? defaultValue : value;
	}
	
	public Integer getInteger(String key){
		Object obj = queryMap.get(key);
		if(obj == null) {
			return null;
		} else {
			try {
				return Integer.parseInt(obj.toString());
			} catch (Exception e) {
				return null;
			}
		}
	}
	
	public Integer getInteger(String key,Integer defaultValue){
		Integer value = getInteger(key);
		return value == null ? defaultValue : value;
	}
	
	public String getString(String key){
		Object obj = queryMap.get(key);
		if(obj == null) {
			return "";
		} else {
			try {
				return obj.toString();
			} catch (Exception e) {
				return "";
			}
		}
	}
	
	public String getString(String key,String defaultValue){
		String value = getString(key);
		return StringTools.isEmpty(value) ? defaultValue : value;
	}
	
	public Long getLong(String key){
		Object obj = queryMap.get(key);
		if(obj == null) {
			return null;
		} else {
			try {
				return Long.parseLong(obj.toString());
			} catch (Exception e) {
				return null;
			}
		}
	}
	
	public Long getLong(String key, Long defaultValue){
		Long value = getLong(key);
		return value == null ? defaultValue : value;
	}
	
	public Date getDate(String key){
		Object obj = queryMap.get(key);
		if(obj == null) {
			return null;
		} else {
			try {
				return (Date)obj;
			} catch (Exception e) {
				return null;
			}
		}
	}
	
	public Date getDate(String key, Date defaultValue){
		Date value = getDate(key);
		return value == null ? defaultValue : value;
	}
	
	public Boolean getBoolean(String key){
		Object obj = queryMap.get(key);
		if(obj == null) {
			return null;
		} else {
			try {
				return Boolean.parseBoolean(obj.toString());
			} catch (Exception e) {
				return null;
			}
		}
	}
	
	public Boolean getBoolean(String key, Boolean defaultValue){
		Boolean value = getBoolean(key);
		return value == null ? defaultValue : value;
	}
	
	protected Date StringToDate(String date){
		Date toDate = DateTools.today();
		try {
			toDate = DateTools.StringToDate(date);
		} catch (Exception e) {
			toDate = null;
		}
		return toDate;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Paging getPaging(int totalSize,int barSize){
		int start = Integer.parseInt(queryMap.get("start").toString());
		int pageSize = Integer.parseInt(queryMap.get("pageSize").toString());
		return new Paging(start,pageSize,totalSize,barSize, queryMap);
	}
}
