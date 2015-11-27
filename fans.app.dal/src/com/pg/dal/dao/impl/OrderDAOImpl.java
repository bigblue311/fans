package com.pg.dal.dao.impl;

import com.pg.dal.dao.OrderDAO;
import com.pg.dal.model.OrderDO;
import com.pg.dal.query.OrderQueryCondition;
import com.victor.framework.dal.basic.EntityDAO;

public class OrderDAOImpl extends EntityDAO<OrderDO,OrderQueryCondition> implements OrderDAO{

	public OrderDAOImpl() {
		super(OrderDO.class.getSimpleName());
	}

	@Override
	public Double getTotalSale(OrderQueryCondition queryCondition) {
		Object result = super.queryForEntity(OrderDO.class.getSimpleName(), 
											 "getTotalSale",
											 queryCondition.getQueryMap());
		if(result!=null){
			return (Double)result;
		} else {
			return 0d;
		}
	}

	@Override
	public Double getTotalDeposit(OrderQueryCondition queryCondition) {
		Object result = super.queryForEntity(OrderDO.class.getSimpleName(), 
				 							 "getTotalDeposit",
				 							 queryCondition.getQueryMap());
		if(result!=null){
			return (Double)result;
		} else {
			return 0d;
		}
	}
}
