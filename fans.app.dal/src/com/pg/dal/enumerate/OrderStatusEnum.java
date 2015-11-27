package com.pg.dal.enumerate;

import java.util.List;

import com.google.common.collect.Lists;

public enum OrderStatusEnum {
	提交("0","已提交","待确认订单","red","<span class='am-badge am-badge-danger am-round'>"),
	确认("1","已确认","待确认发货","blue","<span class='am-badge am-badge-primary am-round'>"),
	发货("2","已发货","待确认收货","orange","<span class='am-badge am-badge-warning am-round'>"),
	收货("3","已收货","待尾款结算","green","<span class='am-badge am-badge-success am-round'>"),
	结算("4","已结算","完成","blueviolet","<span class='am-badge am-badge-secondary am-round'>"),
	取消("5","已取消","取消","grey","<span class='am-badge am-round'>");
	
	private String code;
	private String desc;
	private String next;
	private String color;
	private String badge;
	
	private OrderStatusEnum(String code,String desc, String next, String color, String badge){
		this.code = code;
		this.desc = desc;
		this.next = next;
		this.color = color;
		this.badge = badge;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	public String getNext() {
		return next;
	}
	
	public String getColor() {
		return color;
	}
	
	public String getBadge(){
		return badge;
	}

	public static List<OrderStatusEnum> getAll(){
		return Lists.newArrayList(OrderStatusEnum.values());
	}
	
	public static OrderStatusEnum getByCode(String code){
		for(OrderStatusEnum status : getAll()){
			if(status.code.equals(code)){
				return status;
			}
		}
		return null;
	}
	
	public static OrderStatusEnum getByDesc(String desc){
		for(OrderStatusEnum status : getAll()){
			if(status.desc.equals(desc)){
				return status;
			}
		}
		return null;
	}
	
	public static OrderStatusEnum getByText(String text){
		if(getByCode(text) == null) {
			return getByDesc(text);
		}
		return getByCode(text);
	}
}
