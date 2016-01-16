package com.fans.dal.enumerate;

import java.util.List;

import com.google.common.collect.Lists;

public enum SystemConfigKeyEnum {
	DEBUG_MODE("DEBUG_MODE","调试模式 值ON/OFF 一旦开启将进入系统调试模式"),
	REFRESH_INTERVAL("REFRESH_INTERVAL","刷新置顶时间, 单位分钟"),
	MONEY_COINS_RATIO("MONEY_COINS_RATIO","现金金币购买比例1元=11个金币"),
	VIP_COINS_PER_DAY("VIP_COINS_PER_DAY","金币购买VIP天价格"),
	VIP_MONEY_PER_DAY("VIP_MONEY_PER_DAY","现金购买VIP天价格"),
	TOP_COINS_PER_MINUTE("TOP_COINS_PER_MINUTE","金币购买超级置顶一分钟价格"),
	TOP_MONEY_PER_MINUTE("TOP_MONEY_PER_MINUTE","现金购买超级置顶一分钟价格"),
	TOPUP_SET("TOPUP_SET","充值版面面值设置,单位元,用逗号分割"),
	MEMBER_SET("MEMBER_SET","购买会员面板设置,单位月,用逗号分割"),
	ROCKET_SET("ROCKET_SET","购买火箭面板设置,单位分钟,用逗号分割"),
	TOPUP_PROMOTE("TOPUP_PROMOTE","充值阶梯价格设置,用逗号分割"),
	MEMBER_PROMOTE("MEMBER_PROMOTE","会员阶梯价格,用逗号分割"),
	ROCKET_PROMOTE("ROCKET_PROMOTE","火箭阶梯价格,用逗号分割"),
	SHARE_COINS("SHARE_COINS","分享置顶时间"),
	SHARE_TITLE("SHARE_TITLE","分享标题"),
	SHARE_IMG("SHARE_IMG","分享图标"),
	SHARE_MAX("SHARE_MAX","一天内最多分享置顶次数"),
	WEIXIN_PAY("WEIXIN_PAY","微信支付开关 值ON/OFF 一旦开启将微信支付"),
	SVK_TOP("SVK_TOP","SKV会员免费刷新设置, 单位天"),
	KEFU_ONLINE("KEFU_ONLINE","客服上线时间请以HH:mm:ss表示, 用24小时"),
	KEFU_OFFLINE("KEFU_OFFLINE","客服下线时间请以HH:mm:ss表示, 用24小时"),
	NEW_COINS("NEW_COINS","新注册用户送金币"),
	NEW_VIP("NEW_VIP","新注册用户送VIP");
	
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
