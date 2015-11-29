package com.fans.web.webpage.screen;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.fans.biz.manager.UserManager;
import com.fans.dal.model.UserDO;
import com.fans.dal.query.UserQueryCondition;
import com.fans.web.constant.SessionKey;
import com.victor.framework.common.tools.DateTools;
import com.victor.framework.dal.basic.Paging;

public class Index {
    @Autowired
    private UserManager userManager;
    
    @Autowired
    private HttpSession session;
    
    public void execute(Context context){
        UserQueryCondition userQueryCondition = (UserQueryCondition)session.getAttribute(SessionKey.QUERY);
        if(userQueryCondition == null){
            userQueryCondition = new UserQueryCondition();
            session.setAttribute(SessionKey.QUERY, userQueryCondition);
        }
        userQueryCondition.valid().setPage(1);
        Paging<UserDO> paging = userManager.getPage(userQueryCondition);
        context.put("list", paging.getData());
        
        UserDO userDO = (UserDO) session.getAttribute(SessionKey.USER);
        if(userDO != null){
            context.put("isVip", userDO.isVip());
            context.put("canRefresh", userDO.canRefresh());
            context.put("nextRefresh", userDO.nextRefresh());
        } else {
            context.put("isVip", false);
            context.put("canRefresh", true);
            context.put("nextRefresh", DateTools.today());
        }
    }
}
