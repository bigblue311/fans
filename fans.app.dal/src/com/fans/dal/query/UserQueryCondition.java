package com.fans.dal.query;

import java.util.Date;

import com.victor.framework.common.tools.DateTools;
import com.victor.framework.dal.basic.QueryCondition;

public class UserQueryCondition extends QueryCondition {
	
	public UserQueryCondition setId(Long id) {
		put("id",id);
		return this;
	}
	
	public Long getId(){
		return getLong("id");
	}
	
	public UserQueryCondition setNickName(String nickName) {
		put("nickName",nickName);
		return this;
	}
	
	public String getNickName(){
		return getString("nickName");
	}
	
	public UserQueryCondition setGender(Integer gender) {
		put("gender",gender);
		return this;
	}
	
	public Integer getGender(){
		return getInteger("gender");
	}
	
	public UserQueryCondition setProvince(String province) {
		put("province",province);
		return this;
	}
	
	public String getProvince(){
		return getString("province");
	}
	
	public UserQueryCondition setCity(String city) {
        put("city",city);
        return this;
    }
    
    public String getCity(){
        return getString("city");
    }
    
    public UserQueryCondition setDesciption(String description) {
        put("description",description);
        return this;
    }
    
    public String getDesciption(){
        return getString("description");
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
	
	public UserQueryCondition valid(){
	    super.put("valid", true);
	    return this;
	}

	public UserQueryCondition setGmtCreateStart(Date from){
		put("gmtCreateStart", DateTools.getDayBegin(from));
		return this;
	}
	
	public Date getGmtCreateStart(){
		return getDate("gmtCreateStart");
	}
	
	public UserQueryCondition setGmtCreateEnd(Date to){
		put("gmtCreateEnd", DateTools.getDayEnd(to));
		return this;
	}
	
	public Date getGmtCreateEnd(){
		return getDate("gmtCreateEnd");
	}
	
	@Override
	public UserQueryCondition setGmtModifyStart(Date from){
		put("gmtModifyStart", DateTools.getDayBegin(from));
		return this;
	}
	
	@Override
	public UserQueryCondition setGmtModifyEnd(Date to){
		put("gmtModifyEnd", DateTools.getDayEnd(to));
		return this;
	}
	
	@Override
	public UserQueryCondition setStart(int start){
		put("start", start);
		return this;
	}
	
	@Override
	public UserQueryCondition setPageSize(int pageSize){
		put("pageSize", pageSize);
		return this;
	}
}
