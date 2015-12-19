package com.weixin.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.victor.framework.common.tools.JsonTools;
import com.weixin.model.WxConfig;
import com.weixin.model.WxUser;

public class WeixinServiceImpl implements WeixinService{

    private String appId;
    private String appSecret;
    private String reUrl;
    
    @Override
    public String getOauth2Url() {
        return WxConfig.getOauth2Url(appId, reUrl);
    }

    @Override
    public WxUser getUserInfo(String code) {
        String url = WxConfig.getAccessTokenUrl(appId, appSecret, code);
        String result = getPostReturn(url);
        JSONObject json = JSON.parseObject(result);
        System.out.println("json:"+json);
        
        String openId = json.getString("openid");
        String accessToken = json.getString("access_token");
        
        url = WxConfig.getUserInfoUrl(accessToken, openId);
        result = getPostReturn(url);
        System.out.println("json:"+result);
        WxUser wxUser = JsonTools.fromJson(result, WxUser.class);
        wxUser.setAccessToken(accessToken);
        wxUser.setOpenId(openId);
        return wxUser;
    }

    private String getPostReturn(String urlvalue) {
        String inputLine = "";
        try {
            URL url = new URL(urlvalue);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"UTF-8"));
            inputLine = in.readLine().toString();
        } catch (Exception e) {
            e.printStackTrace();
            inputLine = "";
        }

        return inputLine;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public void setReUrl(String reUrl) {
        this.reUrl = reUrl;
    }
}
