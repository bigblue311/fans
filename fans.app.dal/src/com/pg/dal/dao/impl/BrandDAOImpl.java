package com.pg.dal.dao.impl;

import com.pg.dal.dao.BrandDAO;
import com.pg.dal.model.BrandDO;
import com.pg.dal.query.BrandQueryCondition;
import com.victor.framework.dal.basic.EntityDAO;

public class BrandDAOImpl extends EntityDAO<BrandDO,BrandQueryCondition> implements BrandDAO{

	public BrandDAOImpl() {
		super(BrandDO.class.getSimpleName());
	}

}
