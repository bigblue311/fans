package com.fans.web.webpage.screen;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.fans.dal.cache.SystemConfigCache;
import com.fans.dal.enumerate.SystemConfigKeyEnum;
import com.fans.web.webpage.RequestSessionBase;

public class Help extends RequestSessionBase{
    
    @Autowired
    private SystemConfigCache systemConfigCache;
    
    public void execute(Context context){
        Integer refresh = systemConfigCache.getCacheInteger(SystemConfigKeyEnum.REFRESH_INTERVAL.getCode(), 0);
        context.put("refresh", refresh);
        
        Integer coins = systemConfigCache.getCacheInteger(SystemConfigKeyEnum.NEW_COINS.getCode(), 0);
        context.put("coins", coins);
        
        Integer vip = systemConfigCache.getCacheInteger(SystemConfigKeyEnum.NEW_VIP.getCode(), 0);
        context.put("vip", vip);
    }
}
