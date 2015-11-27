package com.pg.web.admin.webpage.screen.api;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.google.common.collect.Lists;
import com.pg.biz.manager.WarehouseManager;
import com.pg.dal.model.CustomerDO;
import com.pg.dal.model.WarehouseDO;
import com.pg.dal.query.WarehouseQueryCondition;
import com.pg.web.admin.common.AuthenticationToken;
import com.victor.framework.common.shared.Result;

public class GetWarehouse {
	
	@Autowired
	private WarehouseManager warehouseManager;
	
	@Autowired
	private HttpSession session;
	
	public Result<List<WarehouseDO>> execute(@Param("customerId") Long customerId,
								   			 @Param(name="system", defaultValue="false") Boolean system) {
		List<WarehouseDO> list = Lists.newArrayList();
		WarehouseQueryCondition queryCondition = new WarehouseQueryCondition();
		if(system!=null && system){
			queryCondition.setSystem(system);
		} else {
			if(customerId != null) {
				queryCondition.setCustomerId(customerId);
			} else {
				CustomerDO customer = AuthenticationToken.getLoginedCustomer(session);
				if(customer == null){
					return Result.newInstance(list, "登陆已失效", false);
				}
				customerId = customer.getId();
				queryCondition.setCustomerId(customerId);
			}
		}
		list = warehouseManager.getByCondition(queryCondition);
		return Result.newInstance(list, "获取数据成功", true);
	}
}
