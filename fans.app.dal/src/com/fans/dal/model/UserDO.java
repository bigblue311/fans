package com.fans.dal.model;

import java.io.Serializable;
import java.util.Date;

import com.victor.framework.dal.basic.EntityDO;

public class UserDO extends EntityDO implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 9107233004949997492L;
    private String openId;
    private String nickName;
    private String phone;
    private String headImg;
    private Integer gender;
    private String qrcode;
    private String province;
    private String city;
    private String description;
    private String weixin_id;
    private Date gmtRefresh;
    private Date gmtVipExpire;
    private Boolean isTest;
    public String getOpenId() {
        return openId;
    }
    public void setOpenId(String openId) {
        this.openId = openId;
    }
    public String getNickName() {
        return nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getHeadImg() {
        return headImg;
    }
    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }
    public Integer getGender() {
        return gender;
    }
    public void setGender(Integer gender) {
        this.gender = gender;
    }
    public String getQrcode() {
        return qrcode;
    }
    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }
    public String getProvince() {
        return province;
    }
    public void setProvince(String province) {
        this.province = province;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getWeixin_id() {
        return weixin_id;
    }
    public void setWeixin_id(String weixin_id) {
        this.weixin_id = weixin_id;
    }
    public Date getGmtRefresh() {
        return gmtRefresh;
    }
    public void setGmtRefresh(Date gmtRefresh) {
        this.gmtRefresh = gmtRefresh;
    }
    public Date getGmtVipExpire() {
        return gmtVipExpire;
    }
    public void setGmtVipExpire(Date gmtVipExpire) {
        this.gmtVipExpire = gmtVipExpire;
    }
    public Boolean getIsTest() {
        return isTest;
    }
    public void setIsTest(Boolean isTest) {
        this.isTest = isTest;
    }
}
