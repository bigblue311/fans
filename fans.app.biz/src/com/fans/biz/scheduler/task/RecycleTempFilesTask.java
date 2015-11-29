package com.fans.biz.scheduler.task;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;

import com.fans.biz.manager.FileUploadManager;
import com.victor.framework.batch.thread.ScheduledTask;
import com.victor.framework.common.tools.LogTools;

public class RecycleTempFilesTask extends ScheduledTask{

	private static LogTools log = new LogTools(RecycleTempFilesTask.class);
	
	public RecycleTempFilesTask() {
		super(1L,TimeUnit.DAYS);
	}
	
	@Autowired
	private FileUploadManager fileUploadManager;
	
	@Override
	public void doWork() {
		fileUploadManager.recycleTemp();
		log.info("自动回收零时文件执行完成");
	}
}
