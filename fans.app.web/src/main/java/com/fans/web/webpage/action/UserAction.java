package com.fans.web.webpage.action;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.FormGroup;
import com.fans.biz.manager.UserManager;
import com.fans.dal.model.UserDO;
import com.fans.dal.query.UserQueryCondition;
import com.fans.web.constant.SessionKey;

public class UserAction {
    
    @Autowired
    private UserManager userManager;
    
    @Autowired
    private HttpSession session;
    
    public void doQuery(@FormGroup("user") UserDO userDO){
        if(userDO == null){
            userDO = new UserDO();
        }
        UserQueryCondition queryCondtion = userDO.toQueryCondition();
        session.setAttribute(SessionKey.QUERY, queryCondtion);
    }
    
    public void doUpdate(@FormGroup("user") UserDO userDO){
        userManager.update(userDO);
    }
    
    public void doRefresh(@FormGroup("user") UserDO userDO){
        userManager.refresh(userDO.getId());
    }
}
