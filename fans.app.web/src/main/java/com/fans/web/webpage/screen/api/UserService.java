package com.fans.web.webpage.screen.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.fans.biz.manager.UserManager;
import com.fans.biz.threadLocal.RequestSession;
import com.fans.dal.model.UserDO;
import com.fans.dal.query.UserQueryCondition;
import com.victor.framework.common.shared.Result;
import com.victor.framework.dal.basic.Paging;

public class UserService {
    
    @Autowired
    private UserManager userManager;
    
    public Result<List<UserDO>> execute(@Param(name="page", defaultValue="2") int page){
        UserQueryCondition userQueryCondition = RequestSession.queryCondition();
        userQueryCondition.valid().setPage(page);
        Paging<UserDO> paging = userManager.getPage(userQueryCondition);
        return Result.newInstance(paging.getData(), "获取用户数据成功", true);
    }
}
