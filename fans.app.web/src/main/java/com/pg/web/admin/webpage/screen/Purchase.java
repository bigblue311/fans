package com.pg.web.admin.webpage.screen;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Params;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.pg.biz.manager.ProductManager;
import com.pg.biz.manager.TransactionManager;
import com.pg.biz.model.PurchaseVO;
import com.pg.dal.enumerate.ResourceEnum;
import com.pg.dal.query.PurchaseQueryCondition;
import com.pg.web.admin.model.json.CrumbJson;
import com.victor.framework.dal.basic.Paging;

public class Purchase {
	
	@Autowired
	private TransactionManager transactionManager;
	
	@Autowired
	private ProductManager productManager;
	
	public void execute(@Params PurchaseQueryCondition query,
						Context context) {
		setCrumb(context);
		Paging<PurchaseVO> pageList = Paging.emptyPage();
		pageList = transactionManager.getPurchaseVOPage(query);
		
		context.put("query", query);
		context.put("paging", pageList);
		context.put("list", JSONObject.toJSONString(pageList.getData()));
		context.put("packageEnum", productManager.getPackageEnumMap().entrySet());
	}
	
	private void setCrumb(Context context){
		List<CrumbJson> crumbs = Lists.newLinkedList();
		crumbs.add(new CrumbJson(ResourceEnum.交易管理.getName(),ResourceEnum.交易管理.getUri()));
		context.put("crumbs", crumbs);
	}
}
