package com.fans.dal.cache.impl;

import java.util.Map;

import org.springframework.beans.factory.InitializingBean;

import com.fans.dal.cache.RoleCache;
import com.fans.dal.enumerate.EnableEnum;
import com.fans.dal.model.RoleDO;
import com.fans.dal.query.RoleQueryCondition;
import com.google.common.collect.Maps;
import com.victor.framework.dal.cache.StaticCache;

public class RoleCacheImpl extends StaticCache<RoleDO,RoleQueryCondition> implements RoleCache,InitializingBean{

	public RoleCacheImpl() {
		super(RoleDO.class.getSimpleName());
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		super.reload();
	}

	@Override
	public boolean exist(Long id) {
		return super.exist(id.toString());
	}
	
	@Override
	public Map<String,String> getEnumMap(){
		Map<String,String> map = Maps.newLinkedHashMap();
		for(RoleDO e: getAll()){
			if(e.getEditable().equals(EnableEnum.有效.getCode())){
				map.put(getEnumKey(e), getEnumValue(e));
			}
		}
		return map;
	}
}
