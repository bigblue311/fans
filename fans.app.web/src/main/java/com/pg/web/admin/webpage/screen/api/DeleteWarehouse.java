package com.pg.web.admin.webpage.screen.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.pg.biz.manager.WarehouseManager;
import com.pg.dal.model.CustomerDO;
import com.pg.dal.model.WarehouseDO;
import com.pg.web.admin.common.AuthenticationToken;
import com.victor.framework.common.shared.Result;

public class DeleteWarehouse {
	
	@Autowired
	private WarehouseManager warehouseManager;
	
	@Autowired
	private HttpSession session;
	
	public Result<Boolean> execute(@Param("id") Long id){
		CustomerDO customer = AuthenticationToken.getLoginedCustomer(session);
		if(customer == null){
			return Result.newInstance(false, "登陆已失效", false);
		}
		WarehouseDO warehouse = warehouseManager.getById(id);
		if(warehouse == null || warehouse.getCustomerId() == null){
			return Result.newInstance(false, "地址不存在", false);
		}
		if(warehouse.getCustomerId().intValue() != customer.getId().intValue()){
			return Result.newInstance(false, "登陆已失效", false);
		}
		warehouseManager.delete(id);
		return Result.newInstance(true, "删除成功", true);
	}
}
