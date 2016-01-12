package com.fans.biz.model;

import java.util.List;

public class ChaojirmResult {
    private String sta;
    private List<CrawlerVO> flist;
    public String getSta() {
        return sta;
    }
    public void setSta(String sta) {
        this.sta = sta;
    }
    public List<CrawlerVO> getFlist() {
        return flist;
    }
    public void setFlist(List<CrawlerVO> flist) {
        this.flist = flist;
    }
}
