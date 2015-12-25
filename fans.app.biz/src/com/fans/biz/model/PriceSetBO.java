package com.fans.biz.model;

public class PriceSetBO {
	private Integer value;
	private Integer cash;
	private Integer coins;
	private String text;
	private String cashMsg;
	private String coinsMsg;
	
	
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	public Integer getCash() {
		return cash;
	}
	public void setCash(Integer cash) {
		this.cash = cash;
	}
	public Integer getCoins() {
		return coins;
	}
	public void setCoins(Integer coins) {
		this.coins = coins;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getCashMsg() {
		return cashMsg;
	}
	public void setCashMsg(String cashMsg) {
		this.cashMsg = cashMsg;
	}
	public String getCoinsMsg() {
		return coinsMsg;
	}
	public void setCoinsMsg(String coinsMsg) {
		this.coinsMsg = coinsMsg;
	}
}
