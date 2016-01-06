package com.fans.web.webpage.screen;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.fans.biz.manager.UserManager;
import com.fans.dal.cache.SystemConfigCache;
import com.fans.dal.enumerate.SystemConfigKeyEnum;
import com.fans.dal.enumerate.TopListPositionEnum;
import com.fans.dal.model.UserDO;
import com.fans.web.constant.RequestSession;
import com.fans.web.webpage.RequestSessionBase;

public class Vip extends RequestSessionBase{

    @Autowired
    private UserManager userManager;
    
    @Autowired
    private SystemConfigCache systemConfigCache;
    
    public void execute(Context context){
    	loadPriceSet(context);
        UserDO userDO = RequestSession.userDO();
        context.put("user", userDO);
        
        context.put("vipTop", userManager.getValidTop(userDO.getId(), TopListPositionEnum.充值));
        context.put("shareTop", userManager.getValidTop(userDO.getId(), TopListPositionEnum.分享));
        
        Integer max = systemConfigCache.getCacheInteger(SystemConfigKeyEnum.SHARE_MAX.getCode(), 3);
        context.put("shareMax", max);
        
        Integer shareCount = userManager.getTodayShareCount(userDO.getId());
        context.put("shareCount", shareCount);
        
        boolean weixinSwitch = systemConfigCache.getSwitch(SystemConfigKeyEnum.WEIXIN_PAY.getCode());
        context.put("weixinSwitch", weixinSwitch);
    }
}
