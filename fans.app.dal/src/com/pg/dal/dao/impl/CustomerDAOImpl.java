package com.pg.dal.dao.impl;

import com.pg.dal.dao.CustomerDAO;
import com.pg.dal.model.CustomerDO;
import com.pg.dal.query.CustomerQueryCondition;
import com.victor.framework.dal.basic.EntityDAO;

public class CustomerDAOImpl extends EntityDAO<CustomerDO,CustomerQueryCondition> implements CustomerDAO{

	public CustomerDAOImpl() {
		super(CustomerDO.class.getSimpleName());
	}

	@Override
	public CustomerDO login(String mobile, String password) {
		CustomerDO customerDO = new CustomerDO();
		customerDO.setMobile(mobile);
		customerDO.setPassword(password);
		return super.queryForEntity("login", customerDO);
	}
}
