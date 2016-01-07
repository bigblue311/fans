package com.fans.biz.scheduler.task;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;

import com.fans.biz.model.OrderStatisticVO;
import com.fans.biz.manager.TransactionManager;
import com.victor.framework.batch.thread.ScheduledTask;
import com.victor.framework.common.tools.LogTools;

public class OrderStatisticTask extends ScheduledTask{

    public static OrderStatisticVO orderStatisticVO;
    
	private static LogTools log = new LogTools(OrderStatisticTask.class);
	
	public OrderStatisticTask() {
		super(15L,TimeUnit.MINUTES);
	}
	
	@Autowired
	private TransactionManager transactionManager;
	
	@Override
	public void doWork() {
	    orderStatisticVO = transactionManager.getOrderStatisticVO();
		log.info("统计月销售数据成功");
	}
}
