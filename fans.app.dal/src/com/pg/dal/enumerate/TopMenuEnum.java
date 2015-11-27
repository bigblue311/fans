package com.pg.dal.enumerate;

import java.util.List;

import com.google.common.collect.Lists;

public enum TopMenuEnum {
	
	系统管理("topMenuSystem","系统管理"),
	营销营销("topMenuAd","营销营销"),
	产品管理("topMenuProduct","产品管理"),
	客户管理("topMenuCustomer","客户管理"),
	物流管理("topMenuLogistics","物流管理"),
	交易管理("topMenuOrder","交易管理");
	
	private TopMenuEnum(String code,String desc){
		this.code = code;
		this.desc = desc;
	}
	
	private String code;
	private String desc;
	
	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	public static List<TopMenuEnum> getAll(){
		return Lists.newArrayList(TopMenuEnum.values());
	}
	
	public static TopMenuEnum getByCode(String code){
		for(TopMenuEnum menu : getAll()){
			if(menu.code.equals(code)){
				return menu;
			}
		}
		return null;
	}
	
	public static TopMenuEnum getByDesc(String desc){
		for(TopMenuEnum menu : getAll()){
			if(menu.desc.equals(desc)){
				return menu;
			}
		}
		return null;
	}
	
	public static TopMenuEnum getByText(String text){
		if(getByCode(text) == null) {
			return getByDesc(text);
		}
		return getByCode(text);
	}
}
