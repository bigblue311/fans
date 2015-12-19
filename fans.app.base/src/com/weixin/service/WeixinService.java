package com.weixin.service;

import com.weixin.model.WxUser;

public interface WeixinService {
    
    /**
     * 获取Oauth2的授权URL
     * @return
     */
    String getOauth2Url();
    
    /**
     * 获取用户基本信息
     * @return
     */
    WxUser getUserInfo(String code);
}
