package com.pg.dal.dao.impl;

import com.pg.dal.dao.OpLogDAO;
import com.pg.dal.model.OpLogDO;
import com.pg.dal.query.OpLogQueryCondition;
import com.victor.framework.dal.basic.EntityDAO;

public class OpLogDAOImpl extends EntityDAO<OpLogDO,OpLogQueryCondition> implements OpLogDAO{

	public OpLogDAOImpl() {
		super(OpLogDO.class.getSimpleName());
	}

}
