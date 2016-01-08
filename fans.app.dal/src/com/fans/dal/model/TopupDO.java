package com.fans.dal.model;

import java.io.Serializable;

import com.fans.dal.query.TopupQueryCondition;
import com.victor.framework.dal.basic.EntityDO;

public class TopupDO extends EntityDO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3396136844250870787L;
	private String uuid;				//外部订单号
	private String weixinOrderId;		//微信订单号
	private String weixinPrepayResult;	//生成预支付单返回结果
	private String weixinPayResult;		//支付结果
	private Long userId;				//用户ID
	private String openId;				//用户OPEN_ID
	private Integer amount;				//充值金额
	private Integer status;				//状态
	private Integer type;				//业务类型
	private String description;			//业务说明
	private String data1;				//附加字段1
	private String data2;				//附加字段2
	private String data3;				//附加字段3
	private String data4;				//附加字段4
	private String operator;            //操作人
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getWeixinOrderId() {
		return weixinOrderId;
	}
	public void setWeixinOrderId(String weixinOrderId) {
		this.weixinOrderId = weixinOrderId;
	}
	public String getWeixinPrepayResult() {
		return weixinPrepayResult;
	}
	public void setWeixinPrepayResult(String weixinPrepayResult) {
		this.weixinPrepayResult = weixinPrepayResult;
	}
	public String getWeixinPayResult() {
		return weixinPayResult;
	}
	public void setWeixinPayResult(String weixinPayResult) {
		this.weixinPayResult = weixinPayResult;
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
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getData1() {
		return data1;
	}
	public void setData1(String data1) {
		this.data1 = data1;
	}
	public String getData2() {
		return data2;
	}
	public void setData2(String data2) {
		this.data2 = data2;
	}
	public String getData3() {
		return data3;
	}
	public void setData3(String data3) {
		this.data3 = data3;
	}
	public String getData4() {
		return data4;
	}
	public void setData4(String data4) {
		this.data4 = data4;
	}
	public String getOperator() {
        return operator;
    }
    public void setOperator(String operator) {
        this.operator = operator;
    }
    public TopupQueryCondition toQueryCondition(){
		TopupQueryCondition queryCondition = new TopupQueryCondition();
		queryCondition.setQueryMap(this.toMap());
		return queryCondition;
	}
}
