package com.pg.web.admin.webpage.screen.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Params;
import com.pg.biz.manager.WarehouseManager;
import com.pg.dal.model.CustomerDO;
import com.pg.dal.model.WarehouseDO;
import com.pg.web.admin.common.AuthenticationToken;
import com.victor.framework.common.shared.Result;

public class SaveWarehouse {
	@Autowired
	private WarehouseManager warehouseManager;
	
	@Autowired
	private HttpSession session;
	
	public Result<Boolean> execute(@Params WarehouseDO warehouse){
		CustomerDO customer = AuthenticationToken.getLoginedCustomer(session);
		if(customer == null){
			return Result.newInstance(false, "登陆已失效", false);
		}
		if(warehouse == null){
			return Result.newInstance(false, "地址不存在", false);
		}
		warehouse.setCustomerId(customer.getId());
		if(warehouse.getId() == null){
			warehouseManager.create(warehouse);
		} else {
			warehouseManager.update(warehouse);
		}
		return Result.newInstance(true, "更新成功", true);
	}
}
