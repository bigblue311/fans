package com.pg.dal.enumerate;

import java.util.List;

import com.google.common.collect.Lists;

public enum BooleanEnum {
	是("0","是"),
	否("1","否");
	
	private String code;
	private String desc;
	
	private BooleanEnum(String code,String desc){
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	public static List<BooleanEnum> getAll(){
		return Lists.newArrayList(BooleanEnum.values());
	}
	
	public static BooleanEnum getByCode(String code){
		for(BooleanEnum enable : getAll()){
			if(enable.code.equals(code)){
				return enable;
			}
		}
		return null;
	}
	
	public static BooleanEnum getByDesc(String desc){
		for(BooleanEnum enable : getAll()){
			if(enable.desc.equals(desc)){
				return enable;
			}
		}
		return null;
	}
	
	public static BooleanEnum getByText(String text){
		if(getByCode(text) == null) {
			return getByDesc(text);
		}
		return getByCode(text);
	}
}
