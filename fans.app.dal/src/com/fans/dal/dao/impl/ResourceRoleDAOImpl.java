package com.fans.dal.dao.impl;

import com.fans.dal.dao.ResourceRoleDAO;
import com.fans.dal.model.ResourceRoleDO;
import com.fans.dal.query.ResourceRoleQueryCondition;
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
