package com.fans.dal.cache.impl;

import org.springframework.beans.factory.InitializingBean;

import com.fans.dal.cache.LocationCache;
import com.fans.dal.model.LocationDO;
import com.fans.dal.query.LocationQueryCondition;
import com.victor.framework.dal.cache.StaticCache;

public class LocationCacheImpl extends StaticCache<LocationDO,LocationQueryCondition> implements LocationCache,InitializingBean{

	public LocationCacheImpl() {
		super(LocationDO.class.getSimpleName());
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		reload();
	}
}
