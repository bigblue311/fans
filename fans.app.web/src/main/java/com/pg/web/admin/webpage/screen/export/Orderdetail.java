package com.pg.web.admin.webpage.screen.export;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.fastjson.JSONObject;
import com.pg.biz.manager.ProductManager;
import com.pg.biz.manager.TransactionManager;
import com.pg.biz.model.OrderVO;
import com.pg.dal.enumerate.OrderStatusEnum;

public class Orderdetail {
	
	@Autowired
	private TransactionManager transactionManager;
	
	@Autowired
	private ProductManager productManager;
	
	public void execute(@Param("id") Long id,
						Context context) {
		OrderVO order = transactionManager.getOrderVOById(id);
		
		context.put("order", order);
		context.put("statusEnum", OrderStatusEnum.getAll());
		context.put("list", JSONObject.toJSONString(order.getPurchaseList()));
		context.put("publishEnum", JSONObject.toJSONString(productManager.getPublishEnumMap()));
	}
}
