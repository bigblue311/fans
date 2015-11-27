package com.pg.web.admin.webpage.screen;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Params;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.pg.biz.manager.CustomerManager;
import com.pg.biz.manager.WarehouseManager;
import com.pg.dal.enumerate.ResourceEnum;
import com.pg.dal.model.CustomerDO;
import com.pg.dal.model.WarehouseDO;
import com.pg.dal.query.WarehouseQueryCondition;
import com.pg.web.admin.model.json.CrumbJson;
import com.victor.framework.dal.basic.Paging;

public class Warehouse {
	
	@Autowired
	private WarehouseManager warehouseManager;
	
	@Autowired
	private CustomerManager customerManager;
	
	public void execute(@Params WarehouseQueryCondition queryCondition,
						Context context){
		setCrumb(context,queryCondition.getCustomerId());
		Paging<WarehouseDO> pageList = Paging.emptyPage();
		queryCondition.setSystem(false);
		pageList = warehouseManager.getPage(queryCondition);
		
		context.put("query", queryCondition);
		context.put("paging", pageList);
		context.put("list", JSONObject.toJSONString(pageList.getData()));
	}
	
	private void setCrumb(Context context,Long customerId){
		List<CrumbJson> crumbs = Lists.newLinkedList();
		crumbs.add(new CrumbJson(ResourceEnum.客户仓库.getName(),ResourceEnum.客户仓库.getUri()));
		if(customerId != null) {
			CustomerDO customerDO = customerManager.getById(customerId);
			crumbs.add(new CrumbJson(customerDO.getName()+"的仓库",ResourceEnum.客户仓库.getUri()+"?customerId="+customerId));
		}
		context.put("crumbs", crumbs);
	}
}
