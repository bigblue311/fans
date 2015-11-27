package com.pg.biz.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.pg.dal.enumerate.OrderStatusEnum;
import com.pg.dal.model.CustomerDO;
import com.pg.dal.model.OrderDO;
import com.pg.dal.model.OpLogDO;
import com.victor.framework.common.tools.DateTools;

public class OrderVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8722238630359313606L;
	
	private OrderDO orderDO;
	private CustomerDO customerDO;
	private List<OpLogDO> opLogList;
	private List<PurchaseVO> purchaseList;
	
	private String statusBadge = "<span>";
	private String status;
	private String gmtCreate;
	
	public OrderDO getOrderDO() {
		return orderDO;
	}
	public void setOrderDO(OrderDO orderDO) {
		this.orderDO = orderDO;
	}
	public CustomerDO getCustomerDO() {
		return customerDO;
	}
	public void setCustomerDO(CustomerDO customerDO) {
		this.customerDO = customerDO;
	}
	public List<OpLogDO> getOpLogList() {
		return opLogList;
	}
	public void setOpLogList(List<OpLogDO> opLogList) {
		this.opLogList = opLogList;
	}
	public List<PurchaseVO> getPurchaseList() {
		return purchaseList;
	}
	public void setPurchaseList(List<PurchaseVO> purchaseList) {
		this.purchaseList = purchaseList;
	}
	
	public String getStatusBadge() {
		if(orderDO != null){
			String s = orderDO.getStatus();
			OrderStatusEnum orderStatus = OrderStatusEnum.getByCode(s);
			if(orderStatus!=null){
				return orderStatus.getBadge();
			}
		}
		return statusBadge;
	}
	
	public void setStatusBadge(String statusBadge) {
		this.statusBadge = statusBadge;
	}
	
	public String getStatus() {
		if(orderDO != null){
			String s = orderDO.getStatus();
			OrderStatusEnum orderStatus = OrderStatusEnum.getByCode(s);
			if(orderStatus!=null){
				return orderStatus.getDesc();
			}
		}
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getGmtCreate() {
		if(orderDO != null){
			Date s = orderDO.getGmtCreate();
			if(s!=null){
				return DateTools.DateToString(s);
			}
		}
		return gmtCreate;
	}
	
	public void setGmtCreate(String gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
}
