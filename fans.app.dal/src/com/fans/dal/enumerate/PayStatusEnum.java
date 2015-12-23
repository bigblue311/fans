package com.fans.dal.enumerate;

import java.util.List;

import com.google.common.collect.Lists;

public enum PayStatusEnum {
	支付成功(0,"支付成功",true),
	余额不足(1,"余额不足",false),
	账户异常(2,"账户异常",false),
	支付失败(3,"支付失败",false);
	
	private Integer code;
	private String desc;
	private Boolean success;
	
	private PayStatusEnum(Integer code,String desc, Boolean success){
		this.code = code;
		this.desc = desc;
		this.success = success;
	}

	public Integer getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	public Boolean getSuccess() {
		return success;
	}

	public static List<PayStatusEnum> getAll(){
		return Lists.newArrayList(PayStatusEnum.values());
	}
	
	public static PayStatusEnum getByCode(Integer code){
		if(code == null) {
			return null;
		}
		for(PayStatusEnum pay : getAll()){
			if(pay.code.intValue() == code.intValue()){
				return pay;
			}
		}
		return null;
	}
	
	public static PayStatusEnum getByDesc(String desc){
		for(PayStatusEnum pay : getAll()){
			if(pay.desc.equals(desc)){
				return pay;
			}
		}
		return null;
	}
}
