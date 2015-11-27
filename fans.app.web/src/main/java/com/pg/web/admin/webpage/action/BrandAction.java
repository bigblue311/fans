package com.pg.web.admin.webpage.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.FormGroup;
import com.pg.biz.manager.BrandManager;
import com.pg.dal.model.BrandDO;

public class BrandAction {
	
	@Autowired
	private BrandManager brandManager;
	
	public void doUpdate(@FormGroup("brand") BrandDO brandDO) {
		if(brandDO.getId() == null ) {
			brandManager.create(brandDO);
		} else {
			brandManager.update(brandDO);
		}
	}
	
	public void doDelete(@FormGroup("brand") BrandDO brandDO) {
		brandManager.delete(brandDO.getId());
	}
}
