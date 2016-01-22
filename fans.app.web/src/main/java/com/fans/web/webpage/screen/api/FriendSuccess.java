package com.fans.web.webpage.screen.api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.fans.biz.manager.TransactionManager;
import com.fans.biz.manager.UserManager;
import com.fans.biz.threadLocal.RequestSession;
import com.fans.dal.cache.SystemConfigCache;
import com.fans.dal.enumerate.SystemConfigKeyEnum;
import com.fans.dal.model.UserDO;
import com.fans.web.webpage.RequestSessionBase;
import com.victor.framework.common.shared.Result;

public class FriendSuccess extends RequestSessionBase{
    
    @Autowired
    private HttpServletRequest request;
    
    @Autowired
    private TransactionManager transactionManager;
    
    @Autowired
    private SystemConfigCache systemConfigCache;
    
    @Autowired
    private UserManager userManager;
    
    public Result<String> execute(){
        UserDO userDO = RequestSession.userDO();
        if(userDO == null || userDO.getId() == null){
            return Result.newInstance("用户不存在", "用户不存在", false);
        }
        
        Integer max = systemConfigCache.getCacheInteger(SystemConfigKeyEnum.FRIEND_MAX.getCode(), 6);
        
        Integer friendCount = userManager.getTodayFriendCount(userDO.getId());
        if(friendCount!=null && friendCount > max){
            return Result.newInstance("一天最多只能加好友"+max+"次得金币哦", "加好友成功", false);
        }
        transactionManager.friend(userDO);
        
        Integer coins = systemConfigCache.getCacheInteger(SystemConfigKeyEnum.FRIEND_COINS.getCode(), 10);
        userManager.addCoins(userDO.getId(), coins);
        return Result.newInstance("加好友成功", "加好友成功", true);
    }
}
