package com.fans.biz.model;

import com.victor.framework.common.tools.JsonTools;

public class WeixinMessageVO {
    private String touser;
    private String msgtype = "text";
    private WeixinMsgContentVO text;
    public String getTouser() {
        return touser;
    }
    public void setTouser(String touser) {
        this.touser = touser;
    }
    public String getMsgtype() {
        return msgtype;
    }
    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }
    public WeixinMsgContentVO getText() {
        return text;
    }
    public void setText(WeixinMsgContentVO text) {
        this.text = text;
    }
    
    public String toJson(){
        return JsonTools.toJson(this);
    }
    
}
