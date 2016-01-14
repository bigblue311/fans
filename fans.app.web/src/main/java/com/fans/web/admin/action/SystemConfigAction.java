package com.fans.web.admin.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.FormGroup;
import com.fans.dal.cache.SystemConfigCache;
import com.fans.dal.model.SystemConfigDO;

public class SystemConfigAction {
	
	@Autowired
	private SystemConfigCache systemConfigCache;
	
	public void doUpdate(@FormGroup("systemConfig") SystemConfigDO systemConfigDO) {
		systemConfigCache.updateDB(systemConfigDO);
	}
}
