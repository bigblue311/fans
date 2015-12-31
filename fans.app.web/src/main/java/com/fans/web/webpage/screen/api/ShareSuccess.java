package com.fans.web.webpage.screen.api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.fans.biz.manager.TransactionManager;
import com.fans.biz.manager.UserManager;
import com.fans.dal.cache.SystemConfigCache;
import com.fans.dal.enumerate.SystemConfigKeyEnum;
import com.fans.dal.model.UserDO;
import com.fans.web.constant.RequestSession;
import com.fans.web.webpage.RequestSessionBase;
import com.victor.framework.common.shared.Result;
import com.victor.framework.common.tools.StringTools;

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
        UserDO userDO = RequestSession.userDO();
        if(userDO == null || userDO.getId() == null || StringTools.isEmpty(userDO.getOpenId())){
            return Result.newInstance("用户不存在", "用户不存在", false);
        }
        
        Integer minutes = systemConfigCache.getCacheInteger(SystemConfigKeyEnum.SYSTEM_CONFIG_SHARE_INTERVAL.getCode(), 30);
        userManager.share(userDO.getId(), minutes);
        
        return Result.newInstance("成功分享, 获得置顶"+minutes+"分钟", "分享置顶成功", true);
    }
}
