package com.fans.dal.dao.impl;

import com.fans.dal.dao.UserDAO;
import com.fans.dal.model.UserDO;
import com.fans.dal.query.UserQueryCondition;
import com.victor.framework.dal.basic.EntityDAO;

public class UserDAOImpl extends EntityDAO<UserDO,UserQueryCondition> implements UserDAO{

	public UserDAOImpl() {
		super(UserDO.class.getSimpleName());
	}

    @Override
    public UserDO getByOpenId(String openId) {
        return super.queryForEntity("getByOpenId", "openId", openId);
    }

    @Override
    public Boolean refresh(Long id) {
        UserDO forUpdate = new UserDO();
        forUpdate.setId(id);
        return super.updateBySID("refresh", forUpdate);
    }

    @Override
    public Boolean topup(UserDO userDO) {
        return super.updateBySID("topup", userDO);
    }
}
