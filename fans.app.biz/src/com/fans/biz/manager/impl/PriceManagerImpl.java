package com.fans.biz.manager.impl;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.fans.biz.manager.PriceManager;
import com.fans.biz.model.PriceSetVO;
import com.fans.biz.model.PromteVO;
import com.fans.biz.model.SkvTopVO;
import com.fans.biz.threadLocal.RequestSession;
import com.fans.dal.cache.SystemConfigCache;
import com.fans.dal.dao.TopListDAO;
import com.fans.dal.enumerate.ShoppingLevelEnum;
import com.fans.dal.enumerate.SystemConfigKeyEnum;
import com.fans.dal.enumerate.TopListPositionEnum;
import com.fans.dal.model.TopListDO;
import com.fans.dal.model.UserDO;
import com.google.common.collect.Lists;
import com.victor.framework.common.shared.Split;
import com.victor.framework.common.tools.CollectionTools;
import com.victor.framework.common.tools.DateTools;
import com.victor.framework.common.tools.StringTools;

public class PriceManagerImpl implements PriceManager{

	@Autowired
    private SystemConfigCache systemConfigCache;
	
	@Autowired
	private TopListDAO topListDAO;
	
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
		Integer ratio = systemConfigCache.getCacheInteger(SystemConfigKeyEnum.VIP_COINS_PER_DAY.getCode(),200);
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
		Integer ratio = systemConfigCache.getCacheInteger(SystemConfigKeyEnum.VIP_MONEY_PER_DAY.getCode(),20);
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
						priceSetBO.setText(topupCash+"元 "+topupM2C(topupCash)+"金币 "+promteBO.getText());
					}
				}
				if(StringTools.isEmpty(priceSetBO.getText())){
					priceSetBO.setText(topupCash+"元 "+topupM2C(topupCash)+"金币 ");
				}
				priceSetBO.setCashMsg("确定充值"+topupCash+"元购买"+topupM2C(topupCash)+"金币?");
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
					        priceSetBO.setText(day+"天 "+coins+"金币或"+cash+"元 "+promteBO.getText());
					    } else {
					        priceSetBO.setText(day+"天 "+coins+"金币 "+promteBO.getText());
					    }
					}
				}
				if(StringTools.isEmpty(priceSetBO.getText())){
				    if(systemConfigCache.getSwitch(SystemConfigKeyEnum.WEIXIN_PAY.getCode())){
				        priceSetBO.setText(day+"天 "+coins+"金币或"+cash+"元");
				    } else {
				        priceSetBO.setText(day+"天 "+coins+"金币");
				    }
				}
				priceSetBO.setCoinsMsg("确定花费"+coins+"金币 购买会员"+day+"天?");
				priceSetBO.setCashMsg("确定花费"+cash+"元 购买会员"+day+"天?");
				list.add(priceSetBO);
			}
		}
		return list;
	}

	@Override
	public List<PriceSetVO> getZhuangBSet() {
		List<PriceSetVO> list = Lists.newArrayList();
		PriceSetVO free = getSkvPriceSetVO();
		if(free!=null){
		    list.add(free);
		    return list;
		}
		List<Integer> rocketList = getSetList(SystemConfigKeyEnum.ROCKET_SET);
		List<PromteVO> promteList = getPromteList(SystemConfigKeyEnum.ROCKET_PROMOTE);
		if(CollectionTools.isNotEmpty(rocketList)){
			for(Integer minute : rocketList){
				Integer cash = buyZhuangBUseMoney(minute);
				Integer coins = buyZhuangBUseCoins(minute);
				
				PriceSetVO priceSetVO = new PriceSetVO();
				priceSetVO.setValue(minute);
				priceSetVO.setCash(cash);
				priceSetVO.setCoins(coins);
				
				//设置优惠信息
				for(PromteVO promteBO : promteList){
					if(promteBO.match(minute)){
					    if(systemConfigCache.getSwitch(SystemConfigKeyEnum.WEIXIN_PAY.getCode())){
					        priceSetVO.setText(minute+"分钟 "+coins+"金币或"+cash+"元 "+promteBO.getText());
					    } else {
					        priceSetVO.setText(minute+"分钟 "+coins+"金币 "+promteBO.getText());
					    }
					}
				}
				if(StringTools.isEmpty(priceSetVO.getText())){
				    if(systemConfigCache.getSwitch(SystemConfigKeyEnum.WEIXIN_PAY.getCode())){
				        priceSetVO.setText(minute+"分钟 "+coins+"金币或"+cash+"元");
				    } else {
                        priceSetVO.setText(minute+"分钟 "+coins+"金币");
                    }
				}
				priceSetVO.setCoinsMsg("确定花费"+coins+"金币 购买超级置顶"+minute+"分钟?");
				priceSetVO.setCashMsg("确定花费"+cash+"元 购买超级置顶"+minute+"分钟?");
				list.add(priceSetVO);
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

	@Override
	public PriceSetVO getSkvPriceSetVO(){
	    PriceSetVO priceSetVO = new PriceSetVO();
	    UserDO userDO = RequestSession.userDO();
	    if(userDO == null){
	        return null;
	    }
	    ShoppingLevelEnum level = RequestSession.level();
	    if(level == null){
	        return null;
	    }
	    TopListDO topList = topListDAO.getLatestByUserId(userDO.getId(), TopListPositionEnum.SKV置顶.getCode());
	    
	    String configValue = systemConfigCache.getCacheString(SystemConfigKeyEnum.SVK_TOP.getCode(),"");
	    if(StringTools.isEmpty(configValue)){
	        return null;
	    }
	    String[] split = configValue.split(Split.逗号);
	    for(String value : split){
	        SkvTopVO skvTopVO = new SkvTopVO(value);
	        if(skvTopVO.getLevel() == null || !skvTopVO.getLevel().getCode().equals(level.getCode())){
	            continue;
	        }
            if(topList == null){
                priceSetVO.setValue(skvTopVO.getMinute());
                priceSetVO.setCash(0);
                priceSetVO.setCoins(0);
                priceSetVO.setText(level.getDesc()+" 每"+skvTopVO.getDay()+"天可获得一次免费刷新机会");
                priceSetVO.setCoinsMsg("确定超级置顶"+skvTopVO.getMinute()+"分钟?");
                priceSetVO.setCashMsg("确定超级置顶"+skvTopVO.getMinute()+"分钟?");
                return priceSetVO;
            } else {
                Date gmtEnd = topList.getGmtEnd();
                if(DateTools.isDayValid(skvTopVO.getDay(), gmtEnd)){
                    continue;
                }
                priceSetVO.setValue(skvTopVO.getMinute());
                priceSetVO.setCash(0);
                priceSetVO.setCoins(0);
                priceSetVO.setText(level.getDesc()+" 每"+skvTopVO.getDay()+"天可获得一次"+skvTopVO.getMinute()+"分钟免费置顶");
                priceSetVO.setCoinsMsg("确定超级置顶"+skvTopVO.getMinute()+"分钟?");
                priceSetVO.setCashMsg("确定超级置顶"+skvTopVO.getMinute()+"分钟?");
                return priceSetVO;
            }
        }
	    return null;
	}

    @Override
    public String getSkvPriceMsg() {
        UserDO userDO = RequestSession.userDO();
        if(userDO == null){
            return null;
        }
        ShoppingLevelEnum level = RequestSession.level();
        if(level == null){
            return null;
        }
        TopListDO topList = topListDAO.getLatestByUserId(userDO.getId(), TopListPositionEnum.SKV置顶.getCode());
        
        String configValue = systemConfigCache.getCacheString(SystemConfigKeyEnum.SVK_TOP.getCode(),"");
        if(StringTools.isEmpty(configValue)){
            return null;
        }
        String[] split = configValue.split(Split.逗号);
        for(String value : split){
            SkvTopVO skvTopVO = new SkvTopVO(value);
            if(skvTopVO.getLevel() == null || !skvTopVO.getLevel().getCode().equals(level.getCode())){
                continue;
            }
            if(topList == null){
                return level.getDesc()+" 每"+skvTopVO.getDay()+"天可获得一次免费刷新机会";
            } else {
                Date gmtEnd = topList.getGmtEnd();
                if(DateTools.isDayValid(skvTopVO.getDay(), gmtEnd)){
                    Integer interval = skvTopVO.getDay();
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(userDO.getGmtRefresh());
                    cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + interval);
                    Long countDown = cal.getTime().getTime() - DateTools.today().getTime();
                    countDown = countDown / 1000;
                    Integer countDownSec = countDown <= 0 ? 0 : countDown.intValue();
                    int oneHour = 60 * 60;
                    String countDownMsg = countDownSec >= oneHour ? (countDownSec / oneHour) + "小时" : countDownSec + "秒";
                    return countDownMsg+"后可获得一次"+skvTopVO.getMinute()+"分钟免费置顶";
                }
                return level.getDesc()+" 每"+skvTopVO.getDay()+"天可获得一次"+skvTopVO.getMinute()+"分钟免费置顶";
            }
        }
        return null;
    }
}
