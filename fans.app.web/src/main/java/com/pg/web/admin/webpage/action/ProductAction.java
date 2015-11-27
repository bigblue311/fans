package com.pg.web.admin.webpage.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.FormGroup;
import com.pg.biz.manager.ProductManager;
import com.pg.dal.model.ProductDO;
import com.pg.web.admin.model.form.ProductFO;

public class ProductAction {
	
	@Autowired
	private ProductManager productManager;
	
	public void doUpdate(@FormGroup("product") ProductFO productFO){
		ProductDO productDO = productFO.getDO();
		if(productDO.getId() == null){
			productManager.createProduct(productDO);
		} else {
			productManager.updateProduct(productDO);
		}
	}
	
	public void doDelete(@FormGroup("product") ProductDO productDO){
		productManager.deleteProduct(productDO.getId());
	}
	
	public void doRecover(@FormGroup("product") ProductDO productDO){
		productManager.recoverProduct(productDO.getId());
	}
}
