package com.fans.admin.model.form;

public class TopupFO {
    private Long topupId;
    private Long userId;
    private Integer cash;
    private String description;
    
    public Long getTopupId() {
        return topupId;
    }
    public void setTopupId(Long topupId) {
        this.topupId = topupId;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Integer getCash() {
        return cash;
    }
    public void setCash(Integer cash) {
        this.cash = cash;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
