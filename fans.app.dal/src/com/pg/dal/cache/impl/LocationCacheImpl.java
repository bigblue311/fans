package com.pg.dal.cache.impl;

import org.springframework.beans.factory.InitializingBean;

import com.pg.dal.cache.LocationCache;
import com.pg.dal.model.LocationDO;
import com.pg.dal.query.LocationQueryCondition;
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
