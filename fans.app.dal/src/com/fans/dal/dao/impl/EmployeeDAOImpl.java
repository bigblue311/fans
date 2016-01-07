package com.fans.dal.dao.impl;

import com.fans.dal.dao.EmployeeDAO;
import com.fans.dal.model.EmployeeDO;
import com.fans.dal.query.EmployeeQueryCondition;
import com.victor.framework.dal.basic.EntityDAO;

public class EmployeeDAOImpl extends EntityDAO<EmployeeDO,EmployeeQueryCondition> implements EmployeeDAO{

	public EmployeeDAOImpl() {
		super(EmployeeDO.class.getSimpleName());
	}

}
