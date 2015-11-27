package com.pg.web.admin.webpage.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.FormGroup;
import com.pg.biz.manager.CustomerManager;
import com.pg.dal.model.CustomerDO;
import com.victor.framework.common.tools.MD5;
import com.victor.framework.common.tools.StringTools;

public class CustomerAction {
	
	@Autowired
	private CustomerManager customerManager;
	
	public void doUpdate(@FormGroup("customer") CustomerDO customerDO){
		if(customerDO.getId() == null){
			if(!customerManager.checkExist(customerDO.getMobile())){
				generateDefaultPassword(customerDO);
				customerManager.create(customerDO);
			}
		} else {
			CustomerDO exist = customerManager.getById(customerDO.getId());
			if(exist.getMobile().equals(customerDO.getMobile())){
				customerManager.update(customerDO);
			} else {
				if(!customerManager.checkExist(customerDO.getMobile())){
					customerManager.update(customerDO);
				}
			}
		}
	}
	
	private void generateDefaultPassword(CustomerDO customerDO){
		String mobile = customerDO.getMobile();
		mobile = StringTools.reverse(mobile);
		mobile = StringTools.subStr(mobile, 0, 6);
		mobile = StringTools.reverse(mobile);
		mobile = MD5.getMD5(mobile);
		customerDO.setPassword(mobile);
	}
}