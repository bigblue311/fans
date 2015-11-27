package com.pg.web.admin.webpage.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.FormGroup;
import com.pg.biz.manager.ProductManager;
import com.pg.dal.model.PackageDO;

public class PackageAction {
	
	@Autowired
	private ProductManager productManager;
	
	public void doUpdate(@FormGroup("package") PackageDO packageDO){
		if(packageDO.getId() == null){
			productManager.createPackage(packageDO);
		} else {
			productManager.updatePackage(packageDO);
		}
	}
	
	public void doDelete(@FormGroup("package") PackageDO packageDO){
		productManager.deleteProduct(packageDO.getId());
	}
}