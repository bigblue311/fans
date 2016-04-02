package com.fans.web.webpage.screen.api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.fans.biz.manager.TransactionManager;
import com.fans.biz.manager.UserManager;
import com.fans.dal.cache.SystemConfigCache;
import com.fans.dal.enumerate.SystemConfigKeyEnum;
import com.fans.dal.model.UserDO;
import com.fans.web.webpage.RequestSessionBase;
import com.victor.framework.common.shared.Result;

public class ShareSuccess extends RequestSessionBase{
    
    @Autowired
    private HttpServletRequest request;
    
    @Autowired
    private TransactionManager transactionManager;
    
    @Autowired
    private SystemConfigCache systemConfigCache;
    
    @Autowired
    private UserManager userManager;
    
    public Result<String> execute(){
        UserDO userDO = super.getUserDO(request);
        if(userDO == null || userDO.getId() == null){
            return Result.newInstance("用户不存在", "用户不存在", false);
        }
        
        Integer max = systemConfigCache.getCacheInteger(SystemConfigKeyEnum.SHARE_MAX.getCode(), 3);
        
        Integer shareCount = userManager.getTodayShareCount(userDO.getId());
        if(shareCount!=null && shareCount > max){
            return Result.newInstance("一天最多只能分享置顶"+max+"次哦", "分享置顶成功", false);
        }
        transactionManager.share(userDO);
        if(shareCount == 0){
            transactionManager.songVip(userDO.getId(), 1);
            return Result.newInstance("成功分享, 获得激活24小时", "分享置顶成功", true);
        } else {
            Integer coins = systemConfigCache.getCacheInteger(SystemConfigKeyEnum.SHARE_COINS.getCode(), 50);
            userManager.addCoins(userDO.getId(), coins);
            return Result.newInstance("成功分享, 获得"+coins+"金币", "分享置顶成功", true);
        }
    }
}
