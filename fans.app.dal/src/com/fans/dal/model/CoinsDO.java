package com.fans.dal.model;

import java.io.Serializable;

import com.victor.framework.dal.basic.EntityDO;

public class CoinsDO extends EntityDO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5182789300769612197L;
	private Integer type;
	private Integer amount;
	private Long topupId;
	private Long userId;
	private String openId;
	private String description;
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
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
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
