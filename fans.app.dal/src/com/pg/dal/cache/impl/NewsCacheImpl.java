package com.pg.dal.cache.impl;

import org.springframework.beans.factory.InitializingBean;

import com.pg.dal.cache.NewsCache;
import com.pg.dal.model.NewsDO;
import com.pg.dal.query.NewsQueryCondition;
import com.victor.framework.dal.cache.StaticCache;

public class NewsCacheImpl extends StaticCache<NewsDO,NewsQueryCondition> implements NewsCache,InitializingBean{

	public NewsCacheImpl() {
		super(NewsDO.class.getSimpleName());
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		reload();
	}
}
