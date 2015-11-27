package com.pg.dal.dao.impl;

import com.pg.dal.dao.PurchaseDAO;
import com.pg.dal.model.PurchaseDO;
import com.pg.dal.query.PurchaseQueryCondition;
import com.victor.framework.dal.basic.EntityDAO;

public class PurchaseDAOImpl extends EntityDAO<PurchaseDO,PurchaseQueryCondition> implements PurchaseDAO{

	public PurchaseDAOImpl() {
		super(PurchaseDO.class.getSimpleName());
	}
}
