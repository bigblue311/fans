package com.pg.dal.dao.impl;

import com.pg.dal.dao.ProdPackDAO;
import com.pg.dal.model.ProdPackDO;
import com.pg.dal.query.ProdPackQueryCondition;
import com.victor.framework.dal.basic.EntityDAO;

public class ProdPackDAOImpl extends EntityDAO<ProdPackDO,ProdPackQueryCondition> implements ProdPackDAO{

	public ProdPackDAOImpl() {
		super(ProdPackDO.class.getSimpleName());
	}

	@Override
	public Boolean deleteByPackageId(Long packageId) {
		return super.deleteBySID("deleteByPackageId", packageId);
	}

}
