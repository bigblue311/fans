package com.fans.web.webpage.screen;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.fans.dal.cache.SystemConfigCache;
import com.fans.dal.enumerate.SystemConfigKeyEnum;
import com.fans.dal.model.UserDO;
import com.fans.web.constant.RequestSession;
import com.fans.web.webpage.RequestSessionBase;
import com.weixin.model.JsApiConfig;
import com.weixin.service.WeixinService;

public class Share extends RequestSessionBase{

    @Autowired
    private WeixinService weixinService;
    
    @Autowired
    private HttpServletRequest request;
    
    @Autowired
    private SystemConfigCache systemConfigCache;
    
    private final String defaultImg = "http://wetuan123.oss-cn-hangzhou.aliyuncs.com/fans/system/logo.jpeg";
    
    public void execute(Context context){
        UserDO userDO = RequestSession.userDO();
        
        String shareTitle = systemConfigCache.getCacheString(SystemConfigKeyEnum.SHARE_TITLE.getCode(), "[name]躺着把粉加了");
        if(userDO != null && shareTitle.contains("[name]")){
            shareTitle = shareTitle.replace("[name]", userDO.getNickName());
        }
        String shareImg = systemConfigCache.getCacheString(SystemConfigKeyEnum.SHARE_IMG.getCode(), defaultImg);
        context.put("shareTitle", shareTitle);
        context.put("shareImg", shareImg);
        
        String requestUrl = request.getRequestURL().toString();
        requestUrl+=(request.getQueryString()==null?"":"?"+request.getQueryString());
        JsApiConfig config = weixinService.getJsApiConfig(requestUrl);
        context.put("jsApiConfig", config);
    }
}
