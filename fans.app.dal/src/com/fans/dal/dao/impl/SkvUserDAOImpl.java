package com.fans.dal.dao.impl;

import com.fans.dal.dao.SkvUserDAO;
import com.fans.dal.model.SkvUserDO;
import com.fans.dal.query.SkvUserQueryCondition;
import com.victor.framework.dal.basic.EntityDAO;

public class SkvUserDAOImpl extends EntityDAO<SkvUserDO,SkvUserQueryCondition> implements SkvUserDAO{

	public SkvUserDAOImpl() {
		super(SkvUserDO.class.getSimpleName());
	}

}
