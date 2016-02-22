package com.fans.biz.model;

import java.util.List;

public class WeixinMenuVO {
    private String name;
    private String type;
    private String url;
    private List<WeixinMenuVO> sub_button;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public List<WeixinMenuVO> getSub_button() {
        return sub_button;
    }
    public void setSub_button(List<WeixinMenuVO> sub_button) {
        this.sub_button = sub_button;
    }
}
