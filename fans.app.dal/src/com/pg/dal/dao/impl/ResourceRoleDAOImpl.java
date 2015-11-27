package com.pg.dal.dao.impl;

import com.pg.dal.dao.ResourceRoleDAO;
import com.pg.dal.model.ResourceRoleDO;
import com.pg.dal.query.ResourceRoleQueryCondition;
import com.victor.framework.dal.basic.EntityDAO;

public class ResourceRoleDAOImpl extends EntityDAO<ResourceRoleDO,ResourceRoleQueryCondition> implements ResourceRoleDAO{

	public ResourceRoleDAOImpl() {
		super(ResourceRoleDO.class.getSimpleName());
	}

	@Override
	public Boolean deleteByRoleId(Long roleId) {
		return super.deleteBySID("deleteByRoleId", roleId);
	}

}
