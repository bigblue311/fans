package com.pg.web.admin.webpage.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.FormGroup;
import com.pg.biz.manager.CategoryManager;
import com.pg.dal.model.CategoryDO;

public class CategoryAction {
	
	@Autowired
	private CategoryManager CategoryManager;
	
	public void doUpdate(@FormGroup("category") CategoryDO categoryDO) {
		if(categoryDO.getId() == null ) {
			CategoryManager.create(categoryDO);
		} else {
			CategoryManager.update(categoryDO);
		}
	}
	
	public void doDelete(@FormGroup("category") CategoryDO categoryDO) {
		CategoryManager.delete(categoryDO.getId());
	}
}
