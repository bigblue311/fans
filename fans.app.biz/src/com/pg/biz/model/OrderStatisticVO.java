package com.pg.biz.model;

import java.util.List;

public class OrderStatisticVO {
	private int totalCount;
	private List<OrderAlertVO> statusList;
	private Double totalSale;
	private Double totalDeposit;
	
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public List<OrderAlertVO> getStatusList() {
		return statusList;
	}
	public void setStatusList(List<OrderAlertVO> statusList) {
		this.statusList = statusList;
	}
	public Double getTotalSale() {
		return totalSale;
	}
	public void setTotalSale(Double totalSale) {
		this.totalSale = totalSale;
	}
	public Double getTotalDeposit() {
		return totalDeposit;
	}
	public void setTotalDeposit(Double totalDeposit) {
		this.totalDeposit = totalDeposit;
	}
}
