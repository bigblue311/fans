package com.pg.web.admin.webpage.screen.api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Params;
import com.pg.biz.manager.ProductManager;
import com.pg.biz.manager.TransactionManager;
import com.pg.dal.model.PublishDO;
import com.pg.web.admin.model.form.OrderFO;
import com.victor.framework.common.shared.Result;

public class CheckOrderItems {
	@Autowired
	private ProductManager productManager;
	
	@Autowired
	private TransactionManager transactionManager;
	
	public Result<Boolean> execute(@Params OrderFO orderFO) {
		PublishDO publishDO = productManager.getPublishById(orderFO.getPublishId());
		if(publishDO == null || !publishDO.isValid()){
			return Result.newInstance(false, "该商品已下架", false);
		}
		Integer totalQuantity = 0;
		Double totalPrice = 0d;
		
		Map<Long,Integer> map = orderFO.getProductMap();
		
		for(Map.Entry<Long,Integer> entry : map.entrySet()){
			Integer quantity = entry.getValue();
			totalQuantity += quantity;
			totalPrice += getFinalPrice(orderFO.getPublishId(),entry.getKey(),quantity);
		}
		
		Integer limitBuyQuantity = publishDO.getLimitBuyQuantity();
		Double limitButPrice = publishDO.getLimitBuyPrice();
		if(limitBuyQuantity != null){
			if(totalQuantity.intValue()<limitBuyQuantity.intValue()){
				return Result.newInstance(false, "低于了最小预定数量", false);
			}
		}
		
		if(limitButPrice != null){
			if(totalPrice.doubleValue()<(limitButPrice*10000d)){
				return Result.newInstance(false, "低于了最小预定金额", false);
			}
		}
		
		return Result.newInstance(true, "可以购买", true);
	}
	
	private Double getFinalPrice(Long publishId, Long productId, Integer quantity){
		Double price = transactionManager.getFinalPrice(publishId, productId, quantity);
		return quantity * price;
	}
}
