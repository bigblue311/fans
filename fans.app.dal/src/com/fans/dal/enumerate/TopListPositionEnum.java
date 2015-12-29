package com.fans.dal.enumerate;

import java.util.List;

import com.google.common.collect.Lists;

public enum TopListPositionEnum {
	充值(0,"充值置顶"),
	分享(1,"分享置顶"),
	加好友(2,"加好友置顶");
	
	private Integer code;
	private String desc;
	
	private TopListPositionEnum(Integer code,String desc){
		this.code = code;
		this.desc = desc;
	}

	public Integer getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	public static List<TopListPositionEnum> getAll(){
		return Lists.newArrayList(TopListPositionEnum.values());
	}
	
	public static TopListPositionEnum getByCode(Integer code){
		if(code == null) {
			return null;
		}
		for(TopListPositionEnum position : getAll()){
			if(position.code.intValue() == code.intValue()){
				return position;
			}
		}
		return null;
	}
	
	public static TopListPositionEnum getByDesc(String desc){
		for(TopListPositionEnum position : getAll()){
			if(position.desc.equals(desc)){
				return position;
			}
		}
		return null;
	}
}
