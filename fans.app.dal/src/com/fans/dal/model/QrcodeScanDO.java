package com.fans.dal.model;

import java.io.Serializable;

import com.victor.framework.dal.basic.EntityDO;

public class QrcodeScanDO extends EntityDO implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = 6226966480144641839L;
    private Long qrcodeId;
    private String openId;
    private Long skvId;
    private Long upperSkvId;
    public Long getQrcodeId() {
        return qrcodeId;
    }
    public void setQrcodeId(Long qrcodeId) {
        this.qrcodeId = qrcodeId;
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
    public Long getUpperSkvId() {
        return upperSkvId;
    }
    public void setUpperSkvId(Long upperSkvId) {
        this.upperSkvId = upperSkvId;
    }
}
