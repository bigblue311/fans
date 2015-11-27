package com.pg.dal.cache.impl;

import org.springframework.beans.factory.InitializingBean;

import com.pg.dal.cache.SystemConfigCache;
import com.pg.dal.model.SystemConfigDO;
import com.pg.dal.query.SystemConfigQueryCondition;
import com.victor.framework.dal.cache.StaticCache;

public class SystemConfigCacheImpl extends StaticCache<SystemConfigDO,SystemConfigQueryCondition> implements SystemConfigCache,InitializingBean{

	public SystemConfigCacheImpl() {
		super(SystemConfigDO.class.getSimpleName());
	}

	@Override
	public boolean getSwitch(String key) {
		SystemConfigDO switchDO = this.getCache(key);
		if(switchDO==null){
			return false;
		}
		return ON.equals(switchDO.getConfigValue());
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		reload();
	}
}
