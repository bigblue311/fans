package com.fans.biz.scheduler.task;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;

import com.fans.biz.model.OrderStatisticVO;
import com.fans.biz.manager.TransactionManager;
import com.victor.framework.batch.thread.ScheduledTask;

public class OrderStatisticTask extends ScheduledTask{

    public static OrderStatisticVO orderStatisticVO;
    
	public OrderStatisticTask() {
		super(1L,TimeUnit.MINUTES);
	}
	
	@Autowired
	private TransactionManager transactionManager;
	
	@Override
	public void doWork() {
	    orderStatisticVO = transactionManager.getOrderStatisticVO();
	}
}
