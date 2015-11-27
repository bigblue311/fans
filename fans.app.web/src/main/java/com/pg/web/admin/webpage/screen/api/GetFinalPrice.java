package com.pg.web.admin.webpage.screen.api;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.pg.biz.manager.ProductManager;
import com.pg.biz.manager.TransactionManager;
import com.pg.dal.model.ProductDO;
import com.victor.framework.common.shared.Result;

public class GetFinalPrice {
	
	@Autowired
	private TransactionManager transactionManager;
	
	@Autowired
	private ProductManager productManager;
	
	public Result<Double> execute(@Param("publishId")Long publishId,
								 @Param("productId")Long productId,
								 @Param("quantity")Integer quantity){
		ProductDO product = productManager.getProductById(productId);
		Double defaultPrice = product.getPrice100();
		Double price = transactionManager.getFinalPrice(publishId, productId, quantity);
		if(price == 0d){
			return Result.newInstance(defaultPrice, "获取价格失败", false);
		} else {
			return Result.newInstance(price, price+"元/箱", true);
		}
	}
}
