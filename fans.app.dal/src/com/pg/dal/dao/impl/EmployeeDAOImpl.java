package com.pg.dal.dao.impl;

import com.pg.dal.dao.EmployeeDAO;
import com.pg.dal.model.EmployeeDO;
import com.pg.dal.query.EmployeeQueryCondition;
import com.victor.framework.dal.basic.EntityDAO;

public class EmployeeDAOImpl extends EntityDAO<EmployeeDO,EmployeeQueryCondition> implements EmployeeDAO{

	public EmployeeDAOImpl() {
		super(EmployeeDO.class.getSimpleName());
	}

}
