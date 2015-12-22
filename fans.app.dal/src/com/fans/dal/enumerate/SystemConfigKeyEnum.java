package com.fans.dal.enumerate;

import java.util.List;

import com.google.common.collect.Lists;

public enum SystemConfigKeyEnum {
	SYSTEM_DEBUG_MODE("SYSTEM_DEBUG_MODE","调试模式 值ON/OFF 一旦开启将进入系统调试模式"),
	SYSTEM_REFRESH_INTERVAL("SYSTEM_REFRESH_INTERVAL","刷新置顶时间, 单位分钟"),
	SYSTEM_MONEY_COINS_RATIO("SYSTEM_MONEY_COINS_RATIO","现金金币购买比例1元=11个金币"),
	SYSTEM_VIP_COINS_PER_MONTH("SYSTEM_VIP_COINS_PER_MONTH","金币购买VIP一个月价格"),
	SYSTEM_VIP_MONEY_PER_MONTH("SYSTEM_VIP_MONEY_PER_MONTH","现金购买VIP一个月价格"),
	SYSTEM_TOP_COINS_PER_MINUTE("SYSTEM_TOP_COINS_PER_MINUTE","金币购买超级置顶一分钟价格"),
	SYSTEM_TOP_MONEY_PER_MINUTE("SYSTEM_TOP_MONEY_PER_MINUTE","现金购买超级置顶一分钟价格");
	
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
