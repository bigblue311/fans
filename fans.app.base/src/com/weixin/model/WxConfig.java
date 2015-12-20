package com.weixin.model;

import com.victor.framework.common.tools.StringTools;

public class WxConfig {
    
    private static String OAUTH2_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?";
    private static String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?";
    private static String USER_INFO_URL = "https://api.weixin.qq.com/sns/userinfo?";
    public static String UNIFILED_ORDER = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    
    static{
        OAUTH2_URL += "appid=[APP_ID]&";
        OAUTH2_URL += "response_type=code&";
        OAUTH2_URL += "scope=snsapi_userinfo&";
        OAUTH2_URL += "state=1&";
        OAUTH2_URL += "redirect_uri=[RE_URL]";
        OAUTH2_URL += "#wechat_redirect";
        
        
        ACCESS_TOKEN_URL += "appid=[APP_ID]&";
        ACCESS_TOKEN_URL += "secret=[APP_SECRET]&";
        ACCESS_TOKEN_URL += "code=[CODE]&";
        ACCESS_TOKEN_URL += "grant_type=authorization_code";
        
        USER_INFO_URL += "access_token=[ACCESS_TOKEN]&";
        USER_INFO_URL += "openid=[OPEN_ID]&";
        USER_INFO_URL += "lang=zh_CN";
    }
    
    public static String getOauth2Url(String appId, String reUrl){
        if(StringTools.isEmpty(appId)){
            appId = "";
        }
        if(StringTools.isEmpty(reUrl)){
            reUrl = "";
        }
        String url = OAUTH2_URL.replace("[APP_ID]", appId).replace("[RE_URL]", reUrl);
        return url;
    }
    
    public static String getAccessTokenUrl(String appId, String appSecret, String code){
        if(StringTools.isEmpty(appId)){
            appId = "";
        }
        if(StringTools.isEmpty(appSecret)){
            appSecret = "";
        }
        if(StringTools.isEmpty(code)){
            code = "";
        }
        String url = ACCESS_TOKEN_URL.replace("[APP_ID]", appId).replace("[APP_SECRET]", appSecret).replace("[CODE]", code);
        return url;
    }
    
    public static String getUserInfoUrl(String accessToken, String openId){
        if(StringTools.isEmpty(accessToken)){
            accessToken = "";
        }
        if(StringTools.isEmpty(openId)){
            openId = "";
        }
        String url = USER_INFO_URL.replace("[ACCESS_TOKEN]", accessToken).replace("[OPEN_ID]", openId);
        return url;
    }
}
