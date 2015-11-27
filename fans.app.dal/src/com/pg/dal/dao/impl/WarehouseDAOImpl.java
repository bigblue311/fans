package com.pg.dal.dao.impl;

import java.util.Map;

import com.google.common.collect.Maps;
import com.pg.dal.dao.WarehouseDAO;
import com.pg.dal.model.WarehouseDO;
import com.pg.dal.query.WarehouseQueryCondition;
import com.victor.framework.dal.basic.EntityDAO;

public class WarehouseDAOImpl extends EntityDAO<WarehouseDO,WarehouseQueryCondition> implements WarehouseDAO{

	public WarehouseDAOImpl() {
		super(WarehouseDO.class.getSimpleName());
	}

	@Override
	public Map<String, String> getEnumMap(Long customerId) {
		Map<String,String> map = Maps.newLinkedHashMap();
		WarehouseQueryCondition queryCondition = new WarehouseQueryCondition();
		queryCondition.setCustomerId(customerId);
		for(WarehouseDO e: super.getByCondition(queryCondition)){
			map.put(getEnumKey(e), getEnumValue(e));
		}
		return map;
	}

}
