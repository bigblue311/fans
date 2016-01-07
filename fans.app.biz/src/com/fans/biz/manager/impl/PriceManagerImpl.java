package com.fans.biz.manager.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.fans.biz.manager.PriceManager;
import com.fans.biz.model.PriceSetVO;
import com.fans.biz.model.PromteVO;
import com.fans.dal.cache.SystemConfigCache;
import com.fans.dal.enumerate.SystemConfigKeyEnum;
import com.google.common.collect.Lists;
import com.victor.framework.common.shared.Split;
import com.victor.framework.common.tools.CollectionTools;
import com.victor.framework.common.tools.StringTools;

public class PriceManagerImpl implements PriceManager{

	@Autowired
    private SystemConfigCache systemConfigCache;
	
	@Override
	public Integer topupM2C(Integer money) {
		Integer ratio = systemConfigCache.getCacheInteger(SystemConfigKeyEnum.MONEY_COINS_RATIO.getCode(),11);
		Integer price = money * ratio;
		List<PromteVO> list = getPromteList(SystemConfigKeyEnum.TOPUP_PROMOTE);
		for(PromteVO promteBO : list){
			if(promteBO.match(money)){
				return promteBO.afterDiscount(price);
			}
		}
		return price;
	}
	
	@Override
	public Integer buyVipUseCoins(Integer month) {
		Integer ratio = systemConfigCache.getCacheInteger(SystemConfigKeyEnum.VIP_COINS_PER_MONTH.getCode(),200);
		Integer price = month * ratio;
		List<PromteVO> list = getPromteList(SystemConfigKeyEnum.MEMBER_PROMOTE);
		for(PromteVO promteBO : list){
			if(promteBO.match(month)){
				return promteBO.afterDiscount(price);
			}
		}
		return price;
	}

	@Override
	public Integer buyVipUseMoney(Integer month) {
		Integer ratio = systemConfigCache.getCacheInteger(SystemConfigKeyEnum.VIP_MONEY_PER_MONTH.getCode(),20);
		Integer price = month * ratio;
		List<PromteVO> list = getPromteList(SystemConfigKeyEnum.MEMBER_PROMOTE);
		for(PromteVO promteBO : list){
			if(promteBO.match(month)){
				return promteBO.afterDiscount(price);
			}
		}
		return price;
	}

	@Override
	public Integer buyZhuangBUseCoins(Integer minute) {
		Integer ratio = systemConfigCache.getCacheInteger(SystemConfigKeyEnum.TOP_COINS_PER_MINUTE.getCode(),20);
		Integer price = minute * ratio;
		List<PromteVO> list = getPromteList(SystemConfigKeyEnum.ROCKET_PROMOTE);
		for(PromteVO promteBO : list){
			if(promteBO.match(minute)){
				return promteBO.afterDiscount(price);
			}
		}
		return price;
	}

	@Override
	public Integer buyZhuangBUseMoney(Integer minute) {
		Integer ratio = systemConfigCache.getCacheInteger(SystemConfigKeyEnum.TOP_MONEY_PER_MINUTE.getCode(),20);
		Integer price = minute * ratio;
		List<PromteVO> list = getPromteList(SystemConfigKeyEnum.ROCKET_PROMOTE);
		for(PromteVO promteBO : list){
			if(promteBO.match(minute)){
				return promteBO.afterDiscount(price);
			}
		}
		return price;
	}
	
	@Override
	public List<PriceSetVO> getTopupSet() {
		List<PriceSetVO> list = Lists.newArrayList();
		List<Integer> topupList = getSetList(SystemConfigKeyEnum.TOPUP_SET);
		List<PromteVO> promteList = getPromteList(SystemConfigKeyEnum.TOPUP_PROMOTE);
		if(CollectionTools.isNotEmpty(topupList)){
			for(Integer topupCash : topupList){
				PriceSetVO priceSetBO = new PriceSetVO();
				priceSetBO.setValue(topupCash);
				priceSetBO.setCash(topupCash);
				//设置优惠信息
				for(PromteVO promteBO : promteList){
					if(promteBO.match(topupCash)){
						priceSetBO.setText(topupCash+"元 "+topupM2C(topupCash)+"积分 "+promteBO.getText());
					}
				}
				if(StringTools.isEmpty(priceSetBO.getText())){
					priceSetBO.setText(topupCash+"元 "+topupM2C(topupCash)+"积分 ");
				}
				priceSetBO.setCashMsg("确定充值"+topupCash+"元购买"+topupM2C(topupCash)+"积分?");
				list.add(priceSetBO);
			}
		}
		return list;
	}
	
	@Override
	public List<PriceSetVO> getVipSet() {
		List<PriceSetVO> list = Lists.newArrayList();
		List<Integer> vipList = getSetList(SystemConfigKeyEnum.MEMBER_SET);
		List<PromteVO> promteList = getPromteList(SystemConfigKeyEnum.MEMBER_PROMOTE);
		if(CollectionTools.isNotEmpty(vipList)){
			for(Integer day : vipList){
				Integer cash = buyVipUseMoney(day);
				Integer coins = buyVipUseCoins(day);
				
				PriceSetVO priceSetBO = new PriceSetVO();
				priceSetBO.setValue(day);
				priceSetBO.setCash(cash);
				priceSetBO.setCoins(coins);
				//设置优惠信息
				for(PromteVO promteBO : promteList){
					if(promteBO.match(day)){
					    if(systemConfigCache.getSwitch(SystemConfigKeyEnum.WEIXIN_PAY.getCode())){
					        priceSetBO.setText(day+"天 "+coins+"积分或"+cash+"元 "+promteBO.getText());
					    } else {
					        priceSetBO.setText(day+"天 "+coins+"积分 "+promteBO.getText());
					    }
					}
				}
				if(StringTools.isEmpty(priceSetBO.getText())){
				    if(systemConfigCache.getSwitch(SystemConfigKeyEnum.WEIXIN_PAY.getCode())){
				        priceSetBO.setText(day+"天 "+coins+"积分或"+cash+"元");
				    } else {
				        priceSetBO.setText(day+"天 "+coins+"积分");
				    }
				}
				priceSetBO.setCoinsMsg("确定花费"+coins+"积分 购买会员"+day+"天?");
				priceSetBO.setCashMsg("确定花费"+cash+"元 购买会员"+day+"天?");
				list.add(priceSetBO);
			}
		}
		return list;
	}

	@Override
	public List<PriceSetVO> getZhuangBSet() {
		List<PriceSetVO> list = Lists.newArrayList();
		List<Integer> rocketList = getSetList(SystemConfigKeyEnum.ROCKET_SET);
		List<PromteVO> promteList = getPromteList(SystemConfigKeyEnum.ROCKET_PROMOTE);
		if(CollectionTools.isNotEmpty(rocketList)){
			for(Integer minute : rocketList){
				Integer cash = buyZhuangBUseMoney(minute);
				Integer coins = buyZhuangBUseCoins(minute);
				
				PriceSetVO priceSetBO = new PriceSetVO();
				priceSetBO.setValue(minute);
				priceSetBO.setCash(cash);
				priceSetBO.setCoins(coins);
				
				//设置优惠信息
				for(PromteVO promteBO : promteList){
					if(promteBO.match(minute)){
					    if(systemConfigCache.getSwitch(SystemConfigKeyEnum.WEIXIN_PAY.getCode())){
					        priceSetBO.setText(minute+"分钟 "+coins+"积分或"+cash+"元 "+promteBO.getText());
					    } else {
					        priceSetBO.setText(minute+"分钟 "+coins+"积分 "+promteBO.getText());
					    }
					}
				}
				if(StringTools.isEmpty(priceSetBO.getText())){
				    if(systemConfigCache.getSwitch(SystemConfigKeyEnum.WEIXIN_PAY.getCode())){
				        priceSetBO.setText(minute+"分钟 "+coins+"积分或"+cash+"元");
				    } else {
                        priceSetBO.setText(minute+"分钟 "+coins+"积分");
                    }
				}
				priceSetBO.setCoinsMsg("确定花费"+coins+"积分 购买超级置顶"+minute+"分钟?");
				priceSetBO.setCashMsg("确定花费"+cash+"元 购买超级置顶"+minute+"分钟?");
				list.add(priceSetBO);
			}
		}
		return list;
	}
	
	private List<Integer> getSetList(SystemConfigKeyEnum systemConfigKeyEnum){
		List<Integer> list = Lists.newArrayList();
		String configValue = systemConfigCache.getCacheString(systemConfigKeyEnum.getCode(),"");
		if(StringTools.isEmpty(configValue)){
			return list;
		}
		String[] split = configValue.split(Split.逗号);
		if(CollectionTools.isEmpty(split)){
			return list;
		}
		for(String value : split){
			try{
				Integer intValue = Integer.parseInt(value);
				if(intValue != null) {
					list.add(intValue);
				}
			} catch(Exception ex){
				continue;
			}
		}
		CollectionTools.sort(list); //排序, 我竟然写了这种逆天的方法
		Collections.reverse(list);
		return list;
	}
	
	private List<PromteVO> getPromteList(SystemConfigKeyEnum systemConfigKeyEnum){
		List<PromteVO> list = Lists.newArrayList();
		String configValue = systemConfigCache.getCacheString(systemConfigKeyEnum.getCode(), "");
		if(StringTools.isEmpty(configValue)){
			return list;
		}
		String[] split = configValue.split(Split.逗号);
		if(CollectionTools.isEmpty(split)){
			return list;
		}
		for(String value : split){
			PromteVO promteBO = new PromteVO(value);
			list.add(promteBO);
		}
		return list;
	}

}
