package com.pg.biz.model;

import java.util.List;

import com.pg.dal.model.PurchaseDO;
import com.pg.dal.model.PurchaseItemDO;

public class PurchaseVO extends PurchaseDO{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7058663310539472215L;
	private String customerName;
	private String customerMobile;
	private String customerIdCard;
	private String status;
	private List<PurchaseItemDO> itemList;
	private Integer limitBuyQuantity;	//最小预定量
	private Double	limitBuyPrice;		//最小购买金额
	
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerMobile() {
		return customerMobile;
	}
	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}
	public String getCustomerIdCard() {
		return customerIdCard;
	}
	public void setCustomerIdCard(String customerIdCard) {
		this.customerIdCard = customerIdCard;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<PurchaseItemDO> getItemList() {
		return itemList;
	}
	public void setItemList(List<PurchaseItemDO> itemList) {
		this.itemList = itemList;
	}
	public Integer getLimitBuyQuantity() {
		return limitBuyQuantity;
	}
	public void setLimitBuyQuantity(Integer limitBuyQuantity) {
		this.limitBuyQuantity = limitBuyQuantity;
	}
	public Double getLimitBuyPrice() {
		return limitBuyPrice;
	}
	public void setLimitBuyPrice(Double limitBuyPrice) {
		this.limitBuyPrice = limitBuyPrice;
	}
}
