package com.fans.dal.model;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.victor.framework.dal.basic.EntityDO;

public class QrcodeDO extends EntityDO implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1011742223263206124L;
    private Long userId;
    private String openId;
    private Long skvId;
    private String ticket;
    private String domain;
    
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getOpenId() {
        return openId;
    }
    public void setOpenId(String openId) {
        this.openId = openId;
    }
    public Long getSkvId() {
        return skvId;
    }
    public void setSkvId(Long skvId) {
        this.skvId = skvId;
    }
    public String getTicket() {
        return ticket;
    }
    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
    public String getDomain() {
        return domain;
    }
    public void setDomain(String domain) {
        this.domain = domain;
    }
    
    public String getQrcode(){
        String encoded;
        try {
            encoded = URLEncoder.encode(ticket,"utf-8");
            String url = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket="+encoded;
            return url;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        } 
    }
}
