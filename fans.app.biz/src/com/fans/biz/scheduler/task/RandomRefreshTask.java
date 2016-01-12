package com.fans.biz.scheduler.task;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;

import com.fans.biz.manager.UserManager;
import com.victor.framework.batch.thread.ScheduledTask;

public class RandomRefreshTask extends ScheduledTask{

	public RandomRefreshTask() {
		super(30L,TimeUnit.MINUTES);
	}
	
	@Autowired
	private UserManager userManager;
	
	@Override
	public void doWork() {
	    userManager.randomRefresh();
	}
}
