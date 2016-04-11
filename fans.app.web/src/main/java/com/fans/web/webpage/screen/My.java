package com.fans.web.webpage.screen;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.fans.dal.model.UserDO;
import com.fans.web.webpage.RequestSessionBase;

public class My extends RequestSessionBase{

    @Autowired
    private HttpServletRequest request;
    
    public void execute(@Param("success") Boolean success ,Context context){
    	loadPriceSet(request, context);
        UserDO userDO = super.getUserDO(request);
        context.put("user", userDO);
        context.put("success", success);
        context.put("isAdmin", super.isAdmin(request));
        context.put("isOriAdmin", super.isOriAdmin(request));
    }
}
