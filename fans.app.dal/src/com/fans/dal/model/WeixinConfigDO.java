package com.fans.dal.model;

import java.io.Serializable;

import com.victor.framework.annotation.StaticCacheKey;
import com.victor.framework.dal.basic.EntityDO;

/**
 * 微信配置
 * @author victorhan
 *
 */
public class WeixinConfigDO extends EntityDO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 366539580312188554L;
	
	@StaticCacheKey
	private String domain; 		//key
	private String appId;		//value
	private String appSecret;	//描述
	private String reUrl;       //回调URL
	private String mchId;       //商户ID
	private String qrcode;      //二维码
	private String weixin;      //微信号
	private String key;         //支付的key
	private String shareImg;    //分享海报
	
    public String getDomain() {
        return domain;
    }
    public void setDomain(String domain) {
        this.domain = domain;
    }
    public String getAppId() {
        return appId;
    }
    public void setAppId(String appId) {
        this.appId = appId;
    }
    public String getAppSecret() {
        return appSecret;
    }
    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }
    public String getReUrl() {
        return reUrl;
    }
    public void setReUrl(String reUrl) {
        this.reUrl = reUrl;
    }
    public String getMchId() {
        return mchId;
    }
    public void setMchId(String mchId) {
        this.mchId = mchId;
    }
    public String getQrcode() {
        return qrcode;
    }
    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }
    public String getWeixin() {
        return weixin;
    }
    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public String getShareImg() {
        return shareImg;
    }
    public void setShareImg(String shareImg) {
        this.shareImg = shareImg;
    }
}
