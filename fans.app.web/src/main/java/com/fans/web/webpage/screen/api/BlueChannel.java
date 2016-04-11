package com.fans.web.webpage.screen.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.fans.biz.manager.UserManager;
import com.fans.web.constant.CookieKey;
import com.fans.web.webpage.RequestSessionBase;
import com.victor.framework.common.shared.Result;

public class BlueChannel extends RequestSessionBase{
    
    @Autowired
    private UserManager userManager;
    
    @Autowired
    private HttpServletRequest request;
    
    @Autowired
    private HttpServletResponse response;
    
    public Result<String> execute(){
        if(!super.isOriAdmin(request)){
            return Result.newInstance("失败", "没有蓝色通道权限", false);
        }
        String oriOpenId = super.getCookie(request, CookieKey.ORI_OPEN_ID);
        super.setOpenId(response, oriOpenId);
        super.removeCookie(response, CookieKey.ORI_OPEN_ID);
        return Result.newInstance("成功", "成功", true);
    }
}
