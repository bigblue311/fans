package com.pg.biz.scheduler.task;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;

import com.pg.biz.common.OrderMonitorCache;
import com.pg.biz.manager.TransactionManager;
import com.pg.dal.enumerate.OrderStatusEnum;
import com.pg.dal.model.OrderDO;
import com.pg.dal.query.OrderQueryCondition;
import com.victor.framework.batch.thread.ScheduledTask;
import com.victor.framework.common.tools.LogTools;

public class OrderStatisticTask extends ScheduledTask{

	private static LogTools log = new LogTools(OrderStatisticTask.class);
	
	public OrderStatisticTask() {
		super(15L,TimeUnit.MINUTES);
	}
	
	@Autowired
	private TransactionManager transactionManager;
	
	@Override
	public void doWork() {
		OrderQueryCondition queryCondition = new OrderQueryCondition();
		queryCondition.status(OrderStatusEnum.提交.getCode());
		List<OrderDO> list = transactionManager.getOrderDOList(queryCondition);
		OrderMonitorCache.setOrderList(list);
		
		OrderMonitorCache.setOrderStatisticVO(transactionManager.getMonthOrderStatistic());
		log.info("统计月销售数据成功");
	}
}
