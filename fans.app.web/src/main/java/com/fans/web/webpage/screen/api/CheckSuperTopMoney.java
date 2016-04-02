package com.fans.web.webpage.screen.api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.fans.biz.manager.RuleManager;
import com.fans.biz.model.HelpLinkResult;
import com.fans.dal.model.UserDO;
import com.fans.web.webpage.RequestSessionBase;

public class CheckSuperTopMoney extends RequestSessionBase{
    
    @Autowired
    private RuleManager ruleManager;
    
    @Autowired
    private HttpServletRequest request;
    
    public HelpLinkResult execute(){
        UserDO userDO = super.getUserDO(request);
        return ruleManager.checkSuperTopMoney(userDO);
    }
}
