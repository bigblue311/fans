package com.fans.web.webpage.screen;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.fans.biz.manager.UserManager;
import com.fans.dal.model.UserDO;
import com.fans.dal.query.UserQueryCondition;
import com.fans.web.constant.RequestSession;
import com.victor.framework.dal.basic.Paging;

public class Index {
    @Autowired
    private UserManager userManager;
    
    public void execute(Context context){
        UserQueryCondition userQueryCondition = RequestSession.queryCondition();
        userQueryCondition.valid().setPageSize(UserQueryCondition.DEFAULT_PAGE_SIZE).setPage(1);
        Paging<UserDO> paging = userManager.getPage(userQueryCondition);
        context.put("query", userQueryCondition);
        context.put("list", paging.getData());
        
        UserDO userDO = RequestSession.userDO();
        if(userDO != null){
            context.put("user", userDO);
            context.put("isVip", userDO.isVip());
            context.put("canRefresh", userDO.canRefresh());
            context.put("nextRefresh", userDO.nextRefresh());
        } else {
            context.put("isVip", false);
            context.put("canRefresh", true);
            context.put("nextRefresh", 0);
        }
    }
}
