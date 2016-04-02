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

public class FriendSuccess extends RequestSessionBase{
    
    @Autowired
    private HttpServletRequest request;
    
    @Autowired
    private TransactionManager transactionManager;
    
    @Autowired
    private SystemConfigCache systemConfigCache;
    
    @Autowired
    private UserManager userManager;
    
    public Result<Integer> execute(){
        UserDO userDO = super.getUserDO(request);
        if(userDO == null || userDO.getId() == null){
            return Result.newInstance(0, "用户不存在", false);
        }
        
        Integer max = systemConfigCache.getCacheInteger(SystemConfigKeyEnum.FRIEND_MAX.getCode(), 6);
        
        Integer friendCount = userManager.getTodayFriendCount(userDO.getId());
        if(friendCount!=null && friendCount > max){
            return Result.newInstance(0, "一天最多只能加好友"+max+"次得金币哦", false);
        }
        transactionManager.friend(userDO);
        
        Integer coins = systemConfigCache.getCacheInteger(SystemConfigKeyEnum.FRIEND_COINS.getCode(), 10);
        userManager.addCoins(userDO.getId(), coins);
        
        friendCount = userManager.getTodayFriendCount(userDO.getId());
        UserDO updated = userManager.getById(userDO.getId());
        return Result.newInstance(updated.getCoins(), "加好友任务完成+"+friendCount+",获得金币"+coins, true);
    }
}
