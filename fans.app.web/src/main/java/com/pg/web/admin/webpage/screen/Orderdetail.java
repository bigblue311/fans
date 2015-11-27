package com.pg.web.admin.webpage.screen;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.pg.biz.manager.ProductManager;
import com.pg.biz.manager.TransactionManager;
import com.pg.biz.model.OrderVO;
import com.pg.dal.enumerate.OrderStatusEnum;
import com.pg.dal.enumerate.ResourceEnum;
import com.pg.web.admin.model.json.CrumbJson;

public class Orderdetail {
	
	@Autowired
	private TransactionManager transactionManager;
	
	@Autowired
	private ProductManager productManager;
	
	public void execute(@Param("id") Long id,
						Context context) {
		setCrumb(context,id);
		OrderVO order = transactionManager.getOrderVOById(id);
		
		context.put("order", order);
		context.put("statusEnum", OrderStatusEnum.getAll());
		context.put("list", JSONObject.toJSONString(order.getPurchaseList()));
		context.put("packageEnum", productManager.getPackageEnumMap().entrySet());
	}
	
	private void setCrumb(Context context,Long id){
		List<CrumbJson> crumbs = Lists.newLinkedList();
		crumbs.add(new CrumbJson(ResourceEnum.订单管理.getName(),ResourceEnum.订单管理.getUri()));
		if(id != null) {
			crumbs.add(new CrumbJson(ResourceEnum.订单详情.getName()+"["+id+"]",ResourceEnum.订单详情.getUri()+"?id="+id));
		}
		context.put("crumbs", crumbs);
	}
}
