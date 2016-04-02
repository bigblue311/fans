package com.fans.web.webpage.screen.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.fans.biz.manager.UserManager;
import com.fans.dal.model.UserDO;
import com.fans.dal.query.UserQueryCondition;
import com.fans.web.webpage.RequestSessionBase;
import com.victor.framework.common.shared.Result;
import com.victor.framework.dal.basic.Paging;

public class UserService extends RequestSessionBase{
    
    @Autowired
    private UserManager userManager;
    
    @Autowired
    private HttpServletRequest request;
    
    public Result<List<UserDO>> execute(@Param(name="page", defaultValue="2") int page){
        UserQueryCondition userQueryCondition = super.getQuery(request);
        userQueryCondition.valid().setPage(page);
        UserDO userDO = super.getUserDO(request);
        if(userDO != null && userDO.getId()!=null){
            userQueryCondition.setShareUserId(userDO.getId());
        }
        Paging<UserDO> paging = userManager.getPage(userQueryCondition);
        return Result.newInstance(paging.getData(), "获取用户数据成功", true);
    }
}
