package com.pg.web.admin.webpage.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.FormGroup;
import com.pg.biz.manager.WarehouseManager;
import com.pg.dal.model.WarehouseDO;

public class WarehouseAction {
	
	@Autowired
	private WarehouseManager warehouseManager;
	
	public void doUpdate(@FormGroup("warehouse") WarehouseDO warehouseDO) {
		if(warehouseDO.getId() == null ) {
			warehouseManager.create(warehouseDO);
		} else {
			warehouseManager.update(warehouseDO);
		}
	}
	
	public void doDelete(@FormGroup("warehouse") WarehouseDO warehouseDO) {
		warehouseManager.delete(warehouseDO.getId());
	}
}
