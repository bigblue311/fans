package com.fans.dal.enumerate;

import java.util.List;

import com.google.common.collect.Lists;

public enum ExtSourceEnum {
    超级人脉("0","超级人脉");
	
	private String code;
	private String desc;
	
	private ExtSourceEnum(String code,String desc){
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	public static List<ExtSourceEnum> getAll(){
		return Lists.newArrayList(ExtSourceEnum.values());
	}
	
	public static ExtSourceEnum getByCode(String code){
		for(ExtSourceEnum enable : getAll()){
			if(enable.code.equals(code)){
				return enable;
			}
		}
		return null;
	}
	
	public static ExtSourceEnum getByDesc(String desc){
		for(ExtSourceEnum enable : getAll()){
			if(enable.desc.equals(desc)){
				return enable;
			}
		}
		return null;
	}
	
	public static ExtSourceEnum getByText(String text){
		if(getByCode(text) == null) {
			return getByDesc(text);
		}
		return getByCode(text);
	}
}
