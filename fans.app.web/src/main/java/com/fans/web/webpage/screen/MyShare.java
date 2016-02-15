package com.fans.web.webpage.screen;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.fans.biz.manager.WeixinManager;
import com.fans.biz.threadLocal.RequestSession;
import com.fans.dal.cache.SystemConfigCache;
import com.fans.dal.cache.WeixinConfigCache;
import com.fans.dal.enumerate.SystemConfigKeyEnum;
import com.fans.dal.model.QrcodeDO;
import com.fans.dal.model.UserDO;
import com.fans.dal.model.WeixinConfigDO;
import com.fans.web.webpage.RequestSessionBase;
import com.victor.framework.common.tools.DateTools;
import com.victor.framework.common.tools.StringTools;
import com.weixin.model.JsApiConfig;

public class MyShare extends RequestSessionBase{

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
        
        UserDO userDO = RequestSession.userDO();
        context.put("user", userDO);
        context.put("isWeixin", super.isWeixinUser(request));
        QrcodeDO qrcodeDO = weixinManager.getUserQrcode(domain, userDO);
        WeixinConfigDO weixinConfigDO = weixinConfigCache.getCache(domain);
        String shareBgImg = weixinConfigDO.getShareImg();
        if(qrcodeDO!=null && StringTools.isNotEmpty(qrcodeDO.getQrcode())){
            shareBgImg = qrcodeDO.getQrcode();
            context.put("expire", "二维码有效期至:"+DateTools.DateToString(qrcodeDO.getExpire()));
        }
        
        String shareTitle = systemConfigCache.getCacheString(SystemConfigKeyEnum.SHARE_TITLE.getCode(), "[name]躺着把粉加了");
        if(userDO != null && shareTitle.contains("[name]")){
            shareTitle = shareTitle.replace("[name]", userDO.getNickName());
        }
        String shareImg = systemConfigCache.getCacheString(SystemConfigKeyEnum.SHARE_IMG.getCode(), defaultImg);
        context.put("shareTitle", shareTitle);
        context.put("shareImg", shareImg);
        context.put("shareBgImg", shareBgImg);
        
        String requestUrl = request.getRequestURL().toString();
        requestUrl+=(request.getQueryString()==null?"":"?"+request.getQueryString());
        JsApiConfig config = weixinManager.getJsApiConfig(domain,requestUrl);
        context.put("jsApiConfig", config);
    }
}
