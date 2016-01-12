package com.fans.biz.model;

public class CrawlerVO {
    private Long id;
    private String headimg;
    private String city;
    private String username;
    private String qrcode;
    private String remark;
    private String svip;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getHeadimg() {
        return headimg;
    }
    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getQrcode() {
        return qrcode;
    }
    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getSvip() {
        return svip;
    }
    public void setSvip(String svip) {
        this.svip = svip;
    }
}
