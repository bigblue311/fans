package com.pg.dal.cache.impl;

import java.util.Map;

import org.springframework.beans.factory.InitializingBean;

import com.google.common.collect.Maps;
import com.pg.dal.cache.RoleCache;
import com.pg.dal.enumerate.EnableEnum;
import com.pg.dal.model.RoleDO;
import com.pg.dal.query.RoleQueryCondition;
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
