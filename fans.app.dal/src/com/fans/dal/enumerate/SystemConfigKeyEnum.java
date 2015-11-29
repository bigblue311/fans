package com.fans.dal.enumerate;

import java.util.List;

import com.google.common.collect.Lists;

public enum SystemConfigKeyEnum {
	SYSTEM_DEBUG_MODE("SYSTEM_DEBUG_MODE","调试模式 值ON/OFF 一旦开启将进入系统调试模式"),
	SYSTEM_1M_CHARGE("SYSTEM_1M_CHARGE","充值一个月金额"),
	SYSTEM_3M_CHARGE("SYSTEM_3M_CHARGE","充值三个月金额"),
	SYSTEM_6M_CHARGE("SYSTEM_6M_CHARGE","充值六个月金额"),
	SYSTEM_12M_CHARGE("SYSTEM_12M_CHARGE","充值一年金额");
	
	private String code;
	private String desc;
	
	private SystemConfigKeyEnum(String code,String desc){
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	public static List<SystemConfigKeyEnum> getAll(){
		return Lists.newArrayList(SystemConfigKeyEnum.values());
	}
	
	public static SystemConfigKeyEnum getByCode(String code){
		for(SystemConfigKeyEnum key : getAll()){
			if(key.code.equals(code)){
				return key;
			}
		}
		return null;
	}
	
	public static SystemConfigKeyEnum getByDesc(String desc){
		for(SystemConfigKeyEnum key : getAll()){
			if(key.desc.equals(desc)){
				return key;
			}
		}
		return null;
	}
	
	public static SystemConfigKeyEnum getByText(String text){
		if(getByCode(text) == null) {
			return getByDesc(text);
		}
		return getByCode(text);
	}
}
