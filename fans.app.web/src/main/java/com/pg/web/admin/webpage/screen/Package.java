package com.pg.web.admin.webpage.screen;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Params;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.pg.biz.manager.ProductManager;
import com.pg.dal.enumerate.ResourceEnum;
import com.pg.dal.model.PackageDO;
import com.pg.dal.query.PackageQueryCondition;
import com.pg.web.admin.model.json.CrumbJson;
import com.victor.framework.dal.basic.Paging;

public class Package {
	
	@Autowired
	private ProductManager productManager;
	
	public void execute(@Params PackageQueryCondition query,
						Context context){
		setCrumb(context);
		Paging<PackageDO> pageList = Paging.emptyPage();
		pageList = productManager.getPackagePage(query);
		
		context.put("query", query);
		context.put("paging", pageList);
		context.put("list", JSONObject.toJSONString(pageList.getData()));
	}
	
	private void setCrumb(Context context){
		List<CrumbJson> crumbs = Lists.newLinkedList();
		crumbs.add(new CrumbJson(ResourceEnum.产品打包.getName(),ResourceEnum.产品打包.getUri()));
		context.put("crumbs", crumbs);
	}
}
