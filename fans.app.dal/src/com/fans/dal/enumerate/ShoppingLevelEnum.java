package com.fans.dal.enumerate;

import java.util.List;

import com.google.common.collect.Lists;

public enum ShoppingLevelEnum {
	股东("8888","股东"),
	导师("2001","导师"),
	V1("2002","V1"),
	V2("2003","V2"),
	V3("2004","V3"),
	普通("2005","普通");
	
	private String code;
	private String desc;
	
	private ShoppingLevelEnum(String code,String desc){
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	public static List<ShoppingLevelEnum> getAll(){
		return Lists.newArrayList(ShoppingLevelEnum.values());
	}
	
	public static ShoppingLevelEnum getByCode(String code){
		for(ShoppingLevelEnum enable : getAll()){
			if(enable.code.equals(code)){
				return enable;
			}
		}
		return null;
	}
	
	public static ShoppingLevelEnum getByDesc(String desc){
		for(ShoppingLevelEnum enable : getAll()){
			if(enable.desc.equals(desc)){
				return enable;
			}
		}
		return null;
	}
	
	public static ShoppingLevelEnum getByText(String text){
		if(getByCode(text) == null) {
			return getByDesc(text);
		}
		return getByCode(text);
	}
}
