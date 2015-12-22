package com.fans.dal.dao.impl;

import java.util.Date;

import com.fans.dal.dao.UserDAO;
import com.fans.dal.model.UserDO;
import com.fans.dal.query.UserQueryCondition;
import com.victor.framework.common.tools.DateTools;
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
    public Boolean topup(Long id, Integer amount) {
    	UserDO forUpdate = new UserDO();
    	forUpdate.setId(id);
    	forUpdate.setCoins(amount);
        return super.updateBySID("topup", forUpdate);
    }

	@Override
	public Boolean vipExtend(Long id, Date gmtVipExpire) {
		UserDO forUpdate = new UserDO();
    	forUpdate.setId(id);
    	forUpdate.setGmtVipExpire(gmtVipExpire);
        return super.updateBySID("vipExtend", forUpdate);
	}

	@Override
	public Boolean startZhuangB(Long id, Date gmtReserve) {
		UserDO forUpdate = new UserDO();
		forUpdate.setId(id);
		forUpdate.setGmtReserve(gmtReserve);
		forUpdate.setGmtRefresh(DateTools.forever());
		return super.updateBySID("startZhuangB", forUpdate);
	}

	@Override
	public Boolean stopZhuangB() {
		return super.update("stopZhuangB");
	}
}
