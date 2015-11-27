package com.pg.biz.common;

import java.util.List;

import com.pg.biz.model.OrderStatisticVO;
import com.pg.dal.model.OrderDO;

public class OrderMonitorCache {
	private static OrderStatisticVO orderStatisticVO;
	private static List<OrderDO> orderList;

	public static OrderStatisticVO getOrderStatisticVO() {
		return orderStatisticVO;
	}

	public static void setOrderStatisticVO(OrderStatisticVO orderStatisticVO) {
		OrderMonitorCache.orderStatisticVO = orderStatisticVO;
	}

	public static List<OrderDO> getOrderList() {
		return orderList;
	}

	public static void setOrderList(List<OrderDO> orderList) {
		OrderMonitorCache.orderList = orderList;
	}
}
