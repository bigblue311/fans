package com.pg.web.admin.webpage.screen;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Params;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.pg.biz.manager.BrandManager;
import com.pg.biz.manager.CategoryManager;
import com.pg.biz.manager.ProductManager;
import com.pg.dal.enumerate.EnableEnum;
import com.pg.dal.enumerate.ResourceEnum;
import com.pg.dal.model.ProductDO;
import com.pg.dal.query.ProductQueryCondition;
import com.pg.web.admin.model.json.CrumbJson;
import com.victor.framework.dal.basic.Paging;

public class Product {
	
	@Autowired
	private ProductManager productManager;
	
	@Autowired
	private BrandManager brandManager;
	
	@Autowired
	private CategoryManager categoryManager;
	
	public void execute(@Params ProductQueryCondition query,
						Context context){
		setCrumb(context);
		Paging<ProductDO> pageList = Paging.emptyPage();
		pageList = productManager.getProductPage(query);
		
		context.put("query", query);
		context.put("paging", pageList);
		context.put("list", JSONObject.toJSONString(pageList.getData()));
		context.put("enableEnum", EnableEnum.getAll());
		context.put("brandEnum", brandManager.getEnumMap().entrySet());
		context.put("categoryEnum", categoryManager.getEnumMap().entrySet());
	}
	
	private void setCrumb(Context context){
		List<CrumbJson> crumbs = Lists.newLinkedList();
		crumbs.add(new CrumbJson(ResourceEnum.产品管理.getName(),ResourceEnum.产品管理.getUri()));
		context.put("crumbs", crumbs);
	}
}
