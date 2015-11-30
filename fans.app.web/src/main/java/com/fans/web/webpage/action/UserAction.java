package com.fans.web.webpage.action;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.FormGroup;
import com.fans.biz.manager.UserManager;
import com.fans.dal.model.UserDO;
import com.fans.web.constant.RequestSession;
import com.fans.web.webpage.RequestSessionBase;

public class UserAction extends RequestSessionBase{
    
    @Autowired
    private UserManager userManager;
    
    @Autowired
    private HttpServletResponse response;
    
    public void doQuery(@FormGroup("user") UserDO userDO){
        if(userDO == null){
            userDO = new UserDO();
        }
        RequestSession.queryCondition(userDO);
        super.setQuery(response, userDO);
    }
    
    public void doUpdate(@FormGroup("user") UserDO userDO){
        userManager.update(userDO);
    }
    
    public void doRefresh(@FormGroup("user") UserDO userDO){
        userManager.refresh(userDO.getId());
    }
}
