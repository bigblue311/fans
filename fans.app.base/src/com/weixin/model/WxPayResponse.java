package com.weixin.model;

import com.victor.framework.common.tools.DateTools;

public class WxPayResponse {
    private String resultCode;
    private String sign;
    private String mchId;
    private String prepayId;
    private String returnMsg;
    private String appId;
    private String nonceStr;
    private String returnCode;
    private String tradeType;
    private String timestamp;
    private Integer wxVersion;
    private String paySign;
    
    public WxPayResponse(){
        String ts = Long.toString(DateTools.today().getTime());
        this.setTimestamp(ts);
    }
    
    public String getResultCode() {
        return resultCode;
    }
    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }
    public String getSign() {
        return sign;
    }
    public void setSign(String sign) {
        this.sign = sign;
    }
    public String getMchId() {
        return mchId;
    }
    public void setMchId(String mchId) {
        this.mchId = mchId;
    }
    public String getPrepayId() {
        return prepayId;
    }
    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }
    public String getReturnMsg() {
        return returnMsg;
    }
    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }
    public String getAppId() {
        return appId;
    }
    public void setAppId(String appId) {
        this.appId = appId;
    }
    public String getNonceStr() {
        return nonceStr;
    }
    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }
    public String getReturnCode() {
        return returnCode;
    }
    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }
    public String getTradeType() {
        return tradeType;
    }
    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }
    public String getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    public Integer getWxVersion() {
        return wxVersion;
    }
    public void setWxVersion(Integer wxVersion) {
        this.wxVersion = wxVersion;
    }
    public String getPackageValue(){
        return "prepay_id="+prepayId;
    }
    public String getPaySign() {
        return paySign;
    }
    public void setPaySign(String paySign) {
        this.paySign = paySign;
    }
}
