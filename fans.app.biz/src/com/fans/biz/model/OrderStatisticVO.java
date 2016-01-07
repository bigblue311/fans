package com.fans.biz.model;

import java.util.List;

public class OrderStatisticVO {
	private Integer todayShare; //今日分享
	private Integer totalShare; //本月分享
	private Integer totalSale;  //本月充值
    private Integer todaySale;  //今日充值
	private List<OrderAlertVO> statusList; //每个状态笔数
	
	public Integer getTodayShare() {
        return todayShare;
    }
    public void setTodayShare(Integer todayShare) {
        this.todayShare = todayShare;
    }
    public void setTotalShare(Integer totalShare) {
        this.totalShare = totalShare;
    }
    public int getTotalShare() {
        return totalShare;
    }
    public void setTotalShare(int totalShare) {
        this.totalShare = totalShare;
    }
	public Integer getTotalSale() {
		return totalSale;
	}
	public void setTotalSale(Integer totalSale) {
		this.totalSale = totalSale;
	}
    public Integer getTodaySale() {
        return todaySale;
    }
    public void setTodaySale(Integer todaySale) {
        this.todaySale = todaySale;
    }
    public List<OrderAlertVO> getStatusList() {
        return statusList;
    }
    public void setStatusList(List<OrderAlertVO> statusList) {
        this.statusList = statusList;
    }
}
