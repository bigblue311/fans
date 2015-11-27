package com.pg.web.admin.webpage.screen.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.pg.biz.manager.WarehouseManager;
import com.pg.dal.cache.LocationCache;
import com.pg.dal.model.LocationDO;
import com.pg.dal.model.WarehouseDO;
import com.pg.dal.query.WarehouseQueryCondition;
import com.victor.framework.common.shared.Result;

public class GetMyWarehouseCity {
	@Autowired
	private WarehouseManager warehouseManager;
	
	@Autowired
	private LocationCache locationCache;
	
	public Result<List<LocationDO>> execute() {
		List<LocationDO> result = Lists.newArrayList();
		List<WarehouseDO> list = Lists.newArrayList();
		WarehouseQueryCondition queryCondition = new WarehouseQueryCondition();
		queryCondition.setSystem(true);
		list = warehouseManager.getByCondition(queryCondition);
		for(WarehouseDO warehouseDO : list){
			String key = warehouseDO.getProvince()+","+warehouseDO.getCity();
			LocationDO locationDO = locationCache.getCache(key);
			if(locationDO != null){
				result.add(locationDO);
			}
		}
		return Result.newInstance(result, "获取数据成功", !result.isEmpty());
	}
}
