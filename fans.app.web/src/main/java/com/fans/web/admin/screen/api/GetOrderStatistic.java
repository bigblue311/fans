package com.fans.web.admin.screen.api;

import com.fans.biz.model.OrderStatisticVO;
import com.fans.biz.scheduler.task.OrderStatisticTask;


public class GetOrderStatistic {
	
	public OrderStatisticVO execute() {
		return OrderStatisticTask.orderStatisticVO;
	}
}
