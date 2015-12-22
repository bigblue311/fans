package com.fans.dal.enumerate;

import java.util.List;

import com.google.common.collect.Lists;

public enum CoinsTypeEnum {
	充值(0,"充值获得"),
	消费(1,"消费使用"),
	活动(2,"活动获得");
	
	private Integer code;
	private String desc;
	
	private CoinsTypeEnum(Integer code,String desc){
		this.code = code;
		this.desc = desc;
	}

	public Integer getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	public static List<CoinsTypeEnum> getAll(){
		return Lists.newArrayList(CoinsTypeEnum.values());
	}
	
	public static CoinsTypeEnum getByCode(Integer code){
		for(CoinsTypeEnum type : getAll()){
			if(type.code == code){
				return type;
			}
		}
		return null;
	}
	
	public static CoinsTypeEnum getByDesc(String desc){
		for(CoinsTypeEnum type : getAll()){
			if(type.desc.equals(desc)){
				return type;
			}
		}
		return null;
	}
}
