package com.pg.biz.manager.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.pg.biz.manager.WarehouseManager;
import com.pg.dal.cache.LocationCache;
import com.pg.dal.dao.WarehouseDAO;
import com.pg.dal.model.LocationDO;
import com.pg.dal.model.WarehouseDO;
import com.pg.dal.query.WarehouseQueryCondition;
import com.victor.framework.dal.basic.Paging;

public class WarehouseManagerImpl implements WarehouseManager{

	@Autowired
	private WarehouseDAO warehouseDAO;
	
	@Autowired
	private LocationCache locationCache;
	
	@Override
	public void create(WarehouseDO warehouseDO) {
		if(warehouseDO.getCustomerId() == null){
			warehouseDO.setCustomerId(0l);
		}
		warehouseDAO.insert(warehouseDO);
	}

	@Override
	public void update(WarehouseDO warehouseDO) {
		warehouseDAO.update(warehouseDO);
	}

	@Override
	public void delete(Long id) {
		warehouseDAO.delete(id);
	}

	@Override
	public WarehouseDO getById(Long id) {
		return warehouseDAO.getById(id);
	}

	@Override
	public List<WarehouseDO> getByCondition(WarehouseQueryCondition queryCondition) {
		return warehouseDAO.getByCondition(queryCondition);
	}

	@Override
	public Paging<WarehouseDO> getPage(WarehouseQueryCondition queryCondition) {
		int totalSize = warehouseDAO.getCount(queryCondition);
		@SuppressWarnings("unchecked")
		Paging<WarehouseDO> page = queryCondition.getPaging(totalSize, 5);
		List<WarehouseDO> list = warehouseDAO.getPage(queryCondition);
		page.setData(list);
		return page;
	}

	@Override
	public Map<String, String> getEnumMap(Long customerId) {
		return warehouseDAO.getEnumMap(customerId);
	}

	@Override
	public String getFullAddress(WarehouseDO warehouseDO) {
		String address = "";
		if(warehouseDO!=null){
			address = warehouseDO.getAddress();
		}
		return getProvince(warehouseDO) + " " + getCity(warehouseDO) + " " + getTown(warehouseDO) + " "+ address;
	}
	
	@Override
	public String getProvince(WarehouseDO warehouseDO){
		if(warehouseDO == null) {
			return "";
		}
		LocationDO locationDO = locationCache.getCache(warehouseDO.getProvince());
		if(locationDO == null){
			return "";
		}
		return locationDO.getName();
	}
	
	@Override
	public String getCity(WarehouseDO warehouseDO){
		if(warehouseDO == null) {
			return "";
		}
		LocationDO locationDO = locationCache.getCache(warehouseDO.getProvince()+","+warehouseDO.getCity());
		if(locationDO == null){
			return "";
		}
		return locationDO.getName();
	}
	
	@Override
	public String getTown(WarehouseDO warehouseDO){
		if(warehouseDO == null) {
			return "";
		}
		LocationDO locationDO = locationCache.getCache(warehouseDO.getProvince()+","+warehouseDO.getCity()+","+warehouseDO.getTown());
		if(locationDO == null){
			return "";
		}
		return locationDO.getName();
	}
}
