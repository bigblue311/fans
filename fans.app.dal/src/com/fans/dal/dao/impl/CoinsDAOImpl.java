package com.fans.dal.dao.impl;

import com.fans.dal.dao.CoinsDAO;
import com.fans.dal.model.CoinsDO;
import com.fans.dal.query.CoinsQueryCondition;
import com.victor.framework.dal.basic.EntityDAO;

public class CoinsDAOImpl extends EntityDAO<CoinsDO,CoinsQueryCondition> implements CoinsDAO{

	public CoinsDAOImpl() {
		super(CoinsDO.class.getSimpleName());
	}

}
