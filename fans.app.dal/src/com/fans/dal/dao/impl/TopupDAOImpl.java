package com.fans.dal.dao.impl;

import com.fans.dal.dao.TopupDAO;
import com.fans.dal.model.TopupDO;
import com.fans.dal.query.TopupQueryCondition;
import com.victor.framework.dal.basic.EntityDAO;

public class TopupDAOImpl extends EntityDAO<TopupDO,TopupQueryCondition> implements TopupDAO{

	public TopupDAOImpl() {
		super(TopupDO.class.getSimpleName());
	}
}
