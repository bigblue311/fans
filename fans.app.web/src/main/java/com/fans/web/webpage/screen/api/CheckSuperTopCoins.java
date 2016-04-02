package com.fans.web.webpage.screen.api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.fans.biz.manager.RuleManager;
import com.fans.biz.model.HelpLinkResult;
import com.fans.dal.model.UserDO;
import com.fans.web.webpage.RequestSessionBase;

public class CheckSuperTopCoins extends RequestSessionBase{
    
    @Autowired
    private RuleManager ruleManager;
    
    @Autowired
    private HttpServletRequest request;
    
    public HelpLinkResult execute(@Param("coins")int coins){
        UserDO userDO = super.getUserDO(request);
        return ruleManager.checkSuperTopCoins(userDO, coins);
    }
}
