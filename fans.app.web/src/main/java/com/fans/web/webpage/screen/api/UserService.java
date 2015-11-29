package com.fans.web.webpage.screen.api;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.fans.biz.manager.UserManager;
import com.fans.dal.model.UserDO;
import com.fans.dal.query.UserQueryCondition;
import com.fans.web.constant.SessionKey;
import com.victor.framework.common.shared.Result;
import com.victor.framework.dal.basic.Paging;

public class UserService {
    
    @Autowired
    private UserManager userManager;
    
    @Autowired
    private HttpSession session;
    
    public Result<List<UserDO>> more(@Param(name="page", defaultValue="2") int page){
        UserQueryCondition userQueryCondition = (UserQueryCondition)session.getAttribute(SessionKey.QUERY);
        if(userQueryCondition == null){
            userQueryCondition = new UserQueryCondition();
            session.setAttribute(SessionKey.QUERY, userQueryCondition);
        }
        userQueryCondition.valid().setPage(page);
        Paging<UserDO> paging = userManager.getPage(userQueryCondition);
        return Result.newInstance(paging.getData(), "获取用户数据成功", true);
    }
}
