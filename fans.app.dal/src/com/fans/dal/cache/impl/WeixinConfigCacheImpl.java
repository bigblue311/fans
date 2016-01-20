package com.fans.dal.cache.impl;

import org.springframework.beans.factory.InitializingBean;

import com.fans.dal.cache.WeixinConfigCache;
import com.fans.dal.model.WeixinConfigDO;
import com.fans.dal.query.WeixinConfigQueryCondition;
import com.victor.framework.dal.cache.StaticCache;

public class WeixinConfigCacheImpl extends StaticCache<WeixinConfigDO,WeixinConfigQueryCondition> implements WeixinConfigCache,InitializingBean{

	public WeixinConfigCacheImpl() {
		super(WeixinConfigDO.class.getSimpleName());
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		reload();
	}
}
