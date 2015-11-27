package com.pg.web.admin.webpage.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.FormGroup;
import com.pg.dal.cache.SystemConfigCache;
import com.pg.dal.model.SystemConfigDO;

public class SystemConfigAction {
	
	@Autowired
	private SystemConfigCache systemConfigCache;
	
	public void doUpdate(@FormGroup("systemConfig") SystemConfigDO systemConfigDO) {
		systemConfigCache.updateDB(systemConfigDO);
	}
}
