package com.fans.web.webpage.screen.api;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.fans.biz.manager.WeixinManager;
import com.fans.web.webpage.RequestSessionBase;
import com.victor.framework.common.shared.Result;

public class MenuService extends RequestSessionBase{
    
    @Autowired
    private HttpServletRequest request;
    
    @Autowired
    private WeixinManager weixinManager;
    
    public Result<String> execute(){
        String domain = super.getDomain(request);
        String result = weixinManager.publishMenu(domain);
        return Result.newInstance(result, "刷新菜单成功", true);
    }
}
