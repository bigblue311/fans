package com.pg.web.admin.webpage.screen.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.pg.biz.manager.WarehouseManager;
import com.pg.dal.model.CustomerDO;
import com.pg.dal.model.WarehouseDO;
import com.pg.web.admin.common.AuthenticationToken;
import com.victor.framework.common.shared.Result;

public class GetWarehouseById {
	@Autowired
	private WarehouseManager warehouseManager;
	
	@Autowired
	private HttpSession session;
	
	public Result<WarehouseDO> execute(@Param("id") Long id) {
		CustomerDO customer = AuthenticationToken.getLoginedCustomer(session);
		if(customer == null){
			return Result.newInstance(null, "登陆已失效", false);
		}
		WarehouseDO warehouseDO = warehouseManager.getById(id);
		if(customer.getId().intValue() != warehouseDO.getCustomerId().intValue()){
			return Result.newInstance(null, "登陆已失效", false);
		}
		return Result.newInstance(warehouseDO, "获取数据成功", true);
	}
}
