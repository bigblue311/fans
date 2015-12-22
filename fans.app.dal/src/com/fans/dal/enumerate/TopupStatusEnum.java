package com.fans.dal.enumerate;

import java.util.List;

import com.google.common.collect.Lists;

public enum TopupStatusEnum {
	待支付(0,"待支付"),
	成功(1,"成功"),
	失败(2,"失败");
	
	private Integer code;
	private String desc;
	
	private TopupStatusEnum(Integer code,String desc){
		this.code = code;
		this.desc = desc;
	}

	public Integer getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	public static List<TopupStatusEnum> getAll(){
		return Lists.newArrayList(TopupStatusEnum.values());
	}
	
	public static TopupStatusEnum getByCode(Integer code){
		for(TopupStatusEnum status : getAll()){
			if(status.code == code){
				return status;
			}
		}
		return null;
	}
	
	public static TopupStatusEnum getByDesc(String desc){
		for(TopupStatusEnum status : getAll()){
			if(status.desc.equals(desc)){
				return status;
			}
		}
		return null;
	}
}
