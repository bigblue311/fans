package com.pg.web.admin.webpage.screen;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Params;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.pg.biz.manager.WarehouseManager;
import com.pg.dal.enumerate.ResourceEnum;
import com.pg.dal.model.WarehouseDO;
import com.pg.dal.query.WarehouseQueryCondition;
import com.pg.web.admin.model.json.CrumbJson;
import com.victor.framework.dal.basic.Paging;

public class Mywarehouse {
	
	@Autowired
	private WarehouseManager warehouseManager;
	
	public void execute(@Params WarehouseQueryCondition queryCondition,
						Context context){
		setCrumb(context);
		Paging<WarehouseDO> pageList = Paging.emptyPage();
		queryCondition.setSystem(true);
		pageList = warehouseManager.getPage(queryCondition);
		
		context.put("query", queryCondition);
		context.put("paging", pageList);
		context.put("list", JSONObject.toJSONString(pageList.getData()));
	}
	
	private void setCrumb(Context context){
		List<CrumbJson> crumbs = Lists.newLinkedList();
		crumbs.add(new CrumbJson(ResourceEnum.我的仓库.getName(),ResourceEnum.我的仓库.getUri()));
		context.put("crumbs", crumbs);
	}
}
