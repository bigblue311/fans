package com.fans.biz.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.fans.biz.manager.PriceManager;
import com.fans.dal.cache.SystemConfigCache;
import com.fans.dal.enumerate.SystemConfigKeyEnum;

public class PriceManagerImpl implements PriceManager{

	@Autowired
    private SystemConfigCache systemConfigCache;
	
	@Override
	public Integer topupM2C(Integer money) {
		Integer ratio = systemConfigCache.getCacheInteger(SystemConfigKeyEnum.SYSTEM_MONEY_COINS_RATIO.getCode(),11);
		return money * ratio;
	}

	@Override
	public Integer buyVipUseCoins(Integer month) {
		Integer ratio = systemConfigCache.getCacheInteger(SystemConfigKeyEnum.SYSTEM_VIP_COINS_PER_MONTH.getCode(),200);
		return month * ratio;
	}

	@Override
	public Integer buyVipUseMoney(Integer month) {
		Integer ratio = systemConfigCache.getCacheInteger(SystemConfigKeyEnum.SYSTEM_VIP_MONEY_PER_MONTH.getCode(),20);
		return month * ratio;
	}

	@Override
	public Integer buyZhuangBUseCoins(Integer minute) {
		Integer ratio = systemConfigCache.getCacheInteger(SystemConfigKeyEnum.SYSTEM_TOP_COINS_PER_MINUTE.getCode(),20);
		return minute * ratio;
	}

	@Override
	public Integer buyZhuangBUseMoney(Integer minute) {
		Integer ratio = systemConfigCache.getCacheInteger(SystemConfigKeyEnum.SYSTEM_TOP_MONEY_PER_MINUTE.getCode(),20);
		return minute * ratio;
	}

}
