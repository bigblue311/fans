package com.fans.dal.enumerate;

import java.util.List;

import com.google.common.collect.Lists;

public enum TopupTypeEnum {
	充值(0,"会员充值"),
	购买VIP(1,"购买VIP"),
	购买置顶(2,"购买置顶");
	
	private Integer code;
	private String desc;
	
	private TopupTypeEnum(Integer code,String desc){
		this.code = code;
		this.desc = desc;
	}

	public Integer getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	public static List<TopupTypeEnum> getAll(){
		return Lists.newArrayList(TopupTypeEnum.values());
	}
	
	public static TopupTypeEnum getByCode(Integer code){
		for(TopupTypeEnum type : getAll()){
			if(type.code == code){
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
