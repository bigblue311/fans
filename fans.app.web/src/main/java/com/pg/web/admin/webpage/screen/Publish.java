package com.pg.web.admin.webpage.screen;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Params;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.pg.biz.manager.ProductManager;
import com.pg.biz.manager.WarehouseManager;
import com.pg.dal.enumerate.EnableEnum;
import com.pg.dal.enumerate.ResourceEnum;
import com.pg.dal.model.PublishDO;
import com.pg.dal.query.PublishQueryCondition;
import com.pg.web.admin.model.json.CrumbJson;
import com.victor.framework.dal.basic.Paging;

public class Publish {
	
	@Autowired
	private ProductManager productManager;
	
	@Autowired
	private WarehouseManager warehouseManager;
	
	public void execute(@Params PublishQueryCondition query,
						Context context){
		setCrumb(context);
		Paging<PublishDO> pageList = Paging.emptyPage();
		pageList = productManager.getPublishPage(query);
		
		context.put("query", query);
		context.put("paging", pageList);
		context.put("list", JSONObject.toJSONString(pageList.getData()));
		context.put("enableEnum", EnableEnum.getAll());
		context.put("packageEnum", productManager.getPackageEnumMap().entrySet());
		context.put("warehouseEnum", warehouseManager.getEnumMap(0l).entrySet());
	}
	
	private void setCrumb(Context context){
		List<CrumbJson> crumbs = Lists.newLinkedList();
		crumbs.add(new CrumbJson(ResourceEnum.商品发布.getName(),ResourceEnum.商品发布.getUri()));
		context.put("crumbs", crumbs);
	}
}
