package com.fans.dal.enumerate;

import java.util.List;

import com.google.common.collect.Lists;

public enum TopupTypeEnum {
    金币充值(0,"金币充值","red","<span class='am-badge am-badge-danger am-round'>"),
    购买会员(1,"购买会员","blue","<span class='am-badge am-badge-primary am-round'>"),
	购买置顶(2,"购买指定","blueviolet","<span class='am-badge am-badge-secondary am-round'>");
	
	private Integer code;
	private String desc;
	private String color;
	private String badge;
	
	private TopupTypeEnum(Integer code,String desc, String color, String badge){
		this.code = code;
		this.desc = desc;
		this.color = color;
		this.badge = badge;
	}

	public Integer getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	public String getColor() {
        return color;
    }

    public String getBadge() {
        return badge;
    }

    public static List<TopupTypeEnum> getAll(){
		return Lists.newArrayList(TopupTypeEnum.values());
	}
	
	public static TopupTypeEnum getByCode(Integer code){
		if(code == null) {
			return null;
		}
		for(TopupTypeEnum type : getAll()){
			if(type.code.intValue() == code.intValue()){
				return type;
			}
		}
		return null;
	}
	
	public static TopupTypeEnum getByDesc(String desc){
		for(TopupTypeEnum type : getAll()){
			if(type.desc.equals(desc)){
				return type;
			}
		}
		return null;
	}
}
