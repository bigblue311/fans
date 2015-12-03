package com.fans.dal.enumerate;

import java.util.List;

import com.google.common.collect.Lists;

public enum SearchTypeEnum {
	个人二维码("0","个人二维码"),
	群二维码("1","群二维码");
	
	private String code;
	private String desc;
	
	private SearchTypeEnum(String code,String desc){
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	public static List<SearchTypeEnum> getAll(){
		return Lists.newArrayList(SearchTypeEnum.values());
	}
	
	public static SearchTypeEnum getByCode(String code){
		for(SearchTypeEnum enable : getAll()){
			if(enable.code.equals(code)){
				return enable;
			}
		}
		return null;
	}
	
	public static SearchTypeEnum getByDesc(String desc){
		for(SearchTypeEnum enable : getAll()){
			if(enable.desc.equals(desc)){
				return enable;
			}
		}
		return null;
	}
	
	public static SearchTypeEnum getByText(String text){
		if(getByCode(text) == null) {
			return getByDesc(text);
		}
		return getByCode(text);
	}
}
