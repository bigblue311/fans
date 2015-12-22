package com.weixin.service;

import java.util.SortedMap;

import com.weixin.model.WxPayResponse;
import com.weixin.model.WxPayRequest;
import com.weixin.model.WxUser;

public interface WeixinService {
    
    /**
     * 获取Oauth2的授权URL
     * @return
     */
    String getOauth2Url();
    
    /**
     * 预付订单
     * @return
     */
    WxPayResponse getUnifiedorder(WxPayRequest wxPayRequest);
    
    /**
     * 生成微信签名
     * @param parameters
     * @return
     */
    String createSign(SortedMap<String,String> parameters);
    
    /**
     * 获取用户基本信息
     * @return
     */
    WxUser getUserInfo(String code);
}
