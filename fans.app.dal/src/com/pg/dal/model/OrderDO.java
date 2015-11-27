package com.pg.dal.model;

import java.io.Serializable;

import com.pg.dal.query.OrderQueryCondition;
import com.victor.framework.dal.basic.EntityDO;

/**
 * 订单信息
 * @author victorhan
 *
 */
public class OrderDO extends EntityDO implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6072027456711318024L;
	private Long   customerId;		//客户ID
	private String customerName;	//客户姓名
	private String customerMobile;	//客户电话
	private String customerIdCard;	//客户身份证号
	private Double deposit;			//定金
	private Double totalPrice;		//总价(不含运费)
	private Double transportFee;	//运费
	private String status;			//状态
	private String comment;			//备注
	
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
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
	public Double getDeposit() {
		return deposit;
	}
	public void setDeposit(Double deposit) {
		this.deposit = deposit;
	}
	public Double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Double getTransportFee() {
		return transportFee;
	}
	public void setTransportFee(Double transportFee) {
		this.transportFee = transportFee;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public OrderQueryCondition toQueryCondition(){
		OrderQueryCondition queryCondition = new OrderQueryCondition();
		queryCondition.setQueryMap(this.toMap());
		return queryCondition;
	}
}
