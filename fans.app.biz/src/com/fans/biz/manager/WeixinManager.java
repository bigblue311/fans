package com.fans.biz.manager;

import java.io.IOException;
import java.util.Map;
import java.util.SortedMap;

import org.jdom.JDOMException;

import com.weixin.model.JsApiConfig;
import com.weixin.model.WxPayResponse;
import com.weixin.model.WxPayRequest;
import com.weixin.model.WxUser;

public interface WeixinManager {
    
    /**
     * 获取Oauth2的授权URL
     * @return
     */
    String getOauth2Url(String domain);
    
    /**
     * 预付订单
     * @return
     */
    WxPayResponse getUnifiedorder(String domain,WxPayRequest wxPayRequest);
    
    /**
     * 生成微信签名
     * @param parameters
     * @return
     */
    String createSignMD5(String domain,SortedMap<String,String> parameters);
    
    /**
     * 解析XML
     * @param strxml
     * @return
     * @throws IOException 
     * @throws JDOMException 
     */
    Map<String,String> doXMLParse(String strxml) throws JDOMException, IOException;
    
    /**
     * 获取用户基本信息
     * @return
     */
    WxUser getUserInfo(String domain, String code);
    
    /**
     * 获取JSAPI_TICKET
     * @return
     */
    String getJsApiTicket(String domain);
    /**
     * 获取JSAPIconfig
     * @return
     */
    JsApiConfig getJsApiConfig(String domain, String url);
}
