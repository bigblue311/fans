package com.fans.web.webpage.screen;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.fans.biz.manager.WeixinManager;
import com.fans.dal.cache.SystemConfigCache;
import com.fans.dal.cache.WeixinConfigCache;
import com.fans.dal.enumerate.SystemConfigKeyEnum;
import com.fans.dal.model.UserDO;
import com.fans.dal.model.WeixinConfigDO;
import com.fans.web.webpage.RequestSessionBase;
import com.weixin.model.JsApiConfig;

public class Share extends RequestSessionBase{

    @Autowired
    private WeixinManager weixinManager;
    
    @Autowired
    private HttpServletRequest request;
    
    @Autowired
    private SystemConfigCache systemConfigCache;
    
    @Autowired
    private WeixinConfigCache weixinConfigCache;
    
    private final String defaultImg = "http://wetuan123.oss-cn-hangzhou.aliyuncs.com/fans/system/logo.jpeg";
    
    public void execute(Context context){
        String domain = super.getDomain(request);
        WeixinConfigDO weixinConfigDO = weixinConfigCache.getCache(domain);
        
        UserDO userDO = super.getUserDO(request);
        
        String shareTitle = systemConfigCache.getCacheString(SystemConfigKeyEnum.SHARE_TITLE.getCode(), "[name]躺着把粉加了");
        if(userDO != null && shareTitle.contains("[name]")){
            shareTitle = shareTitle.replace("[name]", userDO.getNickName());
        }
        String shareImg = systemConfigCache.getCacheString(SystemConfigKeyEnum.SHARE_IMG.getCode(), defaultImg);
        context.put("shareTitle", shareTitle);
        context.put("shareImg", shareImg);
        context.put("shareBgImg", weixinConfigDO.getShareImg());
        
        String requestUrl = request.getRequestURL().toString();
        requestUrl+=(request.getQueryString()==null?"":"?"+request.getQueryString());
        JsApiConfig config = weixinManager.getJsApiConfig(domain,requestUrl);
        context.put("jsApiConfig", config);
    }
}
