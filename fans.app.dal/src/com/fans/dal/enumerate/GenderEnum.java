package com.fans.dal.enumerate;

import java.util.List;

import com.google.common.collect.Lists;

public enum GenderEnum {
	男("0","男"),
	女("1","女");
	
	private String code;
	private String desc;
	
	private GenderEnum(String code,String desc){
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	public static List<GenderEnum> getAll(){
		return Lists.newArrayList(GenderEnum.values());
	}
	
	public static GenderEnum getByCode(String code){
		for(GenderEnum enable : getAll()){
			if(enable.code.equals(code)){
				return enable;
			}
		}
		return null;
	}
	
	public static GenderEnum getByDesc(String desc){
		for(GenderEnum enable : getAll()){
			if(enable.desc.equals(desc)){
				return enable;
			}
		}
		return null;
	}
	
	public static GenderEnum getByText(String text){
		if(getByCode(text) == null) {
			return getByDesc(text);
		}
		return getByCode(text);
	}
}
