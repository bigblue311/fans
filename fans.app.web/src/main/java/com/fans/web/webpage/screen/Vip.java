package com.fans.web.webpage.screen;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.fans.biz.manager.UserManager;
import com.fans.biz.threadLocal.RequestSession;
import com.fans.dal.cache.SystemConfigCache;
import com.fans.dal.enumerate.SystemConfigKeyEnum;
import com.fans.dal.enumerate.TopListPositionEnum;
import com.fans.dal.model.UserDO;
import com.fans.web.webpage.RequestSessionBase;
import com.victor.framework.common.tools.DateTools;

public class Vip extends RequestSessionBase{

    @Autowired
    private UserManager userManager;
    
    @Autowired
    private SystemConfigCache systemConfigCache;
    
    public void execute(@Param("open") String open, Context context){
    	loadPriceSet(context);
        UserDO userDO = RequestSession.userDO();
        context.put("user", userDO);
        
        Integer refreshInterval = systemConfigCache.getCacheInteger(SystemConfigKeyEnum.REFRESH_INTERVAL.getCode(), 5);
        context.put("refreshInterval", refreshInterval);
        
        context.put("vipTopCount", userManager.getTopUsers().size());
        context.put("vipTopCountPlusOne", userManager.getTopUsers().size()+1);
        context.put("vipTop", userManager.getValidTop(userDO.getId(), TopListPositionEnum.充值));
        
        Integer shareMax = systemConfigCache.getCacheInteger(SystemConfigKeyEnum.SHARE_MAX.getCode(), 3);
        context.put("shareMax", shareMax);
        
        Integer shareCoins = systemConfigCache.getCacheInteger(SystemConfigKeyEnum.SHARE_COINS.getCode(), 50);
        context.put("shareCoins", shareCoins);
        
        Integer shareCount = userManager.getTodayShareCount(userDO.getId());
        context.put("shareCount", shareCount);
        
        Integer friendMax = systemConfigCache.getCacheInteger(SystemConfigKeyEnum.FRIEND_MAX.getCode(), 6);
        context.put("friendMax", friendMax);
        
        Integer friendCoins = systemConfigCache.getCacheInteger(SystemConfigKeyEnum.FRIEND_COINS.getCode(), 10);
        context.put("friendCoins", friendCoins);
        
        Integer friendCount = userManager.getTodayFriendCount(userDO.getId());
        context.put("friendCount", friendCount);
        
        String kefuOnline = systemConfigCache.getCacheString(SystemConfigKeyEnum.KEFU_ONLINE.getCode(), "09:00:00");
        context.put("kefuOnline", kefuOnline);
        
        String kefuOffline = systemConfigCache.getCacheString(SystemConfigKeyEnum.KEFU_OFFLINE.getCode(), "09:00:00");
        context.put("kefuOffline", kefuOffline);
        
        try {
            boolean isKefuOnline = DateTools.inTime(kefuOnline, kefuOffline);
            context.put("isKefuOnline", isKefuOnline);
        } catch (ParseException e) {
            context.put("isKefuOnline", false);
        }
        
        boolean weixinSwitch = systemConfigCache.getSwitch(SystemConfigKeyEnum.WEIXIN_PAY.getCode());
        context.put("weixinSwitch", weixinSwitch);
        
        context.put("open", open);
    }
}
