package com.fans.web.webpage.screen;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.fans.dal.cache.SystemConfigCache;
import com.fans.dal.cache.WeixinConfigCache;
import com.fans.dal.enumerate.SystemConfigKeyEnum;
import com.fans.dal.model.WeixinConfigDO;
import com.fans.web.webpage.RequestSessionBase;
import com.victor.framework.common.tools.DateTools;

public class Weixin extends RequestSessionBase{
    
    @Autowired
    private HttpServletRequest request;
    
    @Autowired
    private WeixinConfigCache weixinConfigCache;
    
    @Autowired
    private SystemConfigCache systemConfigCache;
    
    public void execute(Context context){
        String domain = super.getDomain(request);
        WeixinConfigDO weixinConfigDO = weixinConfigCache.getCache(domain);
        if(weixinConfigDO!=null){
            context.put("qrcode", weixinConfigDO.getQrcode());
            context.put("weixin", weixinConfigDO.getWeixin());
            context.put("ts", DateTools.getRandomId());
        }
        
        Integer refresh = systemConfigCache.getCacheInteger(SystemConfigKeyEnum.REFRESH_INTERVAL.getCode(), 0);
        context.put("refresh", refresh);
        
        Integer coins = systemConfigCache.getCacheInteger(SystemConfigKeyEnum.NEW_COINS.getCode(), 0);
        context.put("coins", coins);
        
        Integer vip = systemConfigCache.getCacheInteger(SystemConfigKeyEnum.NEW_VIP.getCode(), 0);
        context.put("vip", vip);
    }
}
