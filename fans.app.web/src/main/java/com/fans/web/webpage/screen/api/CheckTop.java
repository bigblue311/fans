package com.fans.web.webpage.screen.api;

import org.springframework.beans.factory.annotation.Autowired;

import com.fans.biz.manager.RuleManager;
import com.fans.biz.model.HelpLinkResult;
import com.fans.biz.threadLocal.RequestSession;
import com.fans.dal.model.UserDO;
import com.fans.web.webpage.RequestSessionBase;

public class CheckTop extends RequestSessionBase{
    
    @Autowired
    private RuleManager ruleManager;
    
    public HelpLinkResult execute(){
        UserDO userDO = RequestSession.userDO();
        return ruleManager.checkTop(userDO);
    }
}
