package com.fans.web.webpage.screen.api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.fans.biz.manager.UserManager;
import com.fans.dal.model.UserDO;
import com.fans.web.webpage.RequestSessionBase;
import com.victor.framework.common.shared.Result;

public class UserClick extends RequestSessionBase{
    
    @Autowired
    private UserManager userManager;
    
    @Autowired
    private HttpServletRequest request;
    
    public Result<String> execute(@Param("id") Long id){
        userManager.click(id);
        UserDO userDO = userManager.getById(id);
        if(userDO != null && userDO.getSkvId()!=null){
            userManager.createSkvUser(super.getOpenId(request), userDO.getSkvId().toString());
        }
        return Result.newInstance("成功", "成功", true);
    }
}
