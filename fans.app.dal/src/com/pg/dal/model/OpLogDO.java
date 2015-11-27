package com.pg.dal.model;

import java.io.Serializable;

import com.pg.dal.query.OpLogQueryCondition;
import com.victor.framework.dal.basic.EntityDO;

/**
 * 操作日志
 * @author victorhan
 *
 */
public class OpLogDO extends EntityDO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6946275515253603978L;
	private Long employeeId;	//操作员ID
	private String action;		//操作记录
	private Long orderId;		//订单ID
	
	public Long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	
	public String getMsg(){
		return action.replace(":", "->").replace(";", "<br>");
	}
	
	public OpLogQueryCondition toQueryCondition(){
		OpLogQueryCondition queryCondition = new OpLogQueryCondition();
		queryCondition.setQueryMap(this.toMap());
		return queryCondition;
	}
}
