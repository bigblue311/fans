package com.weixin.service;

import java.util.SortedMap;

import com.weixin.model.WxPay;
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
    WxPay getUnifiedorder(String openId);
    
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
