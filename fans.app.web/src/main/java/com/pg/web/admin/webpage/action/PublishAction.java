package com.pg.web.admin.webpage.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.FormGroup;
import com.pg.biz.manager.ProductManager;
import com.pg.dal.model.PublishDO;
import com.pg.web.admin.model.form.PublishFO;

public class PublishAction {
	
	@Autowired
	private ProductManager productManager;
	
	public void doUpdate(@FormGroup("publish") PublishFO publishFO){
		PublishDO publishDO = publishFO.getDO();
		if(publishDO.getId() == null){
			productManager.createPublish(publishDO);
		} else {
			productManager.updatePublish(publishDO);
		}
	}
	
	public void doDelete(@FormGroup("publish") PublishFO publishFO){
		productManager.deletePublish(publishFO.getId());
	}
	
	public void doRecover(@FormGroup("publish") PublishFO publishFO){
		productManager.recoverPublish(publishFO.getId());
	}
}
