package com.fans.dal.model;

import java.io.Serializable;

import com.fans.dal.query.TopupQueryCondition;
import com.victor.framework.dal.basic.EntityDO;

public class TopupDO extends EntityDO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3396136844250870787L;
	private String weixinOrderId;		//微信订单号
	private Long userId;				//用户ID
	private String openId;				//用户OPEN_ID
	private Integer amount;				//充值金额
	private Integer status;				//状态
	
	public String getWeixinOrderId() {
		return weixinOrderId;
	}
	public void setWeixinOrderId(String weixinOrderId) {
		this.weixinOrderId = weixinOrderId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public TopupQueryCondition toQueryCondition(){
		TopupQueryCondition queryCondition = new TopupQueryCondition();
		queryCondition.setQueryMap(this.toMap());
		return queryCondition;
	}
}
