package com.fans.web.webpage.screen.api;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.fans.biz.manager.UserManager;
import com.victor.framework.common.shared.Result;

public class UserClick {
    
    @Autowired
    private UserManager userManager;
    
    public Result<String> execute(@Param("id") Long id){
        userManager.click(id);
        return Result.newInstance("成功", "成功", true);
    }
}
