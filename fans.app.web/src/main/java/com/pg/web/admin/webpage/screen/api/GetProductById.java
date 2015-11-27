package com.pg.web.admin.webpage.screen.api;


import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.pg.biz.manager.ProductManager;
import com.pg.dal.model.ProductDO;
import com.victor.framework.common.shared.Result;

public class GetProductById {
	
	@Autowired
	private ProductManager productManager;
	
	public Result<ProductDO> execute(@Param("id")Long id) {
	    ProductDO productDO = productManager.getProductById(id);
		return Result.newInstance(productDO, "获取数据成功", productDO!=null);
	}
}
