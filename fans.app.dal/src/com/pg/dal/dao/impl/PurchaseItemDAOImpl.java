package com.pg.dal.dao.impl;

import com.pg.dal.dao.PurchaseItemDAO;
import com.pg.dal.model.PurchaseItemDO;
import com.pg.dal.query.PurchaseItemQueryCondition;
import com.victor.framework.dal.basic.EntityDAO;

public class PurchaseItemDAOImpl extends EntityDAO<PurchaseItemDO,PurchaseItemQueryCondition> implements PurchaseItemDAO{

	public PurchaseItemDAOImpl() {
		super(PurchaseItemDO.class.getSimpleName());
	}
}
