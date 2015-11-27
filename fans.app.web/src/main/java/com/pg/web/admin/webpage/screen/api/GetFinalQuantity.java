package com.pg.web.admin.webpage.screen.api;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.pg.biz.manager.ProductManager;
import com.pg.biz.manager.TransactionManager;
import com.pg.dal.model.ProductDO;
import com.victor.framework.common.shared.Result;

public class GetFinalQuantity {
	
	@Autowired
	private TransactionManager transactionManager;
	
	@Autowired
	private ProductManager productManager;
	
	public Result<Integer> execute(@Param("purchaseItemId")Long purchaseItemId,
								 @Param("productId")Long productId,
								 @Param("quantity")Integer quantity){
	    ProductDO productDO = productManager.getProductById(productId);
		Integer allowedQuantity = transactionManager.checkQuantity(productDO, purchaseItemId, quantity);
		return Result.newInstance(allowedQuantity, "获取价格失败", true);
	}
}
