package com.fans.web.webpage.screen.api;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.fans.biz.manager.RuleManager;
import com.fans.biz.model.HelpLinkResult;
import com.fans.biz.threadLocal.RequestSession;
import com.fans.dal.model.UserDO;
import com.fans.web.webpage.RequestSessionBase;

public class CheckSuperTopCoins extends RequestSessionBase{
    
    @Autowired
    private RuleManager ruleManager;
    
    public HelpLinkResult execute(@Param("coins")int coins){
        UserDO userDO = RequestSession.userDO();
        return ruleManager.checkSuperTopCoins(userDO, coins);
    }
}
