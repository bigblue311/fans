package com.pg.web.admin.webpage.screen.api;

import com.pg.biz.common.OrderMonitorCache;
import com.pg.biz.model.OrderStatisticVO;

public class GetOrderStatistic {
	
	public OrderStatisticVO execute() {
		return OrderMonitorCache.getOrderStatisticVO();
	}
}
