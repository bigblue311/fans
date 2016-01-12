package com.fans.biz.scheduler.task;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;

import com.aliyun.service.FileStorageRepository;
import com.victor.framework.batch.thread.ScheduledTask;

public class RecycleTempFilesTask extends ScheduledTask{

	public RecycleTempFilesTask() {
		super(1L,TimeUnit.DAYS);
	}
	
	@Autowired
    private FileStorageRepository fileStorageRepository;
	
	@Override
	public void doWork() {
		fileStorageRepository.recycleTemp();
	}
}
