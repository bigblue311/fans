package com.fans.web.webpage.screen.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.fans.biz.manager.UserManager;
import com.fans.dal.model.UserDO;
import com.fans.web.constant.CookieKey;
import com.fans.web.webpage.RequestSessionBase;
import com.victor.framework.common.shared.Result;

public class GreenChannel extends RequestSessionBase{
    
    @Autowired
    private UserManager userManager;
    
    @Autowired
    private HttpServletRequest request;
    
    @Autowired
    private HttpServletResponse response;
    
    public Result<String> execute(@Param("id") Long id){
        if(!super.isAdmin(request)){
            return Result.newInstance("失败", "没有绿色通道权限", false);
        }
        
        UserDO userDO = userManager.getById(id);
        if(userDO==null){
            return Result.newInstance("失败", "用户不存在", false);
        }
        super.setCookie(response, CookieKey.ORI_OPEN_ID, super.getOpenId(request));
        super.setCookie(response, CookieKey.OPEN_ID, userDO.getOpenId());
        
        return Result.newInstance("成功", "成功", true);
    }
}
