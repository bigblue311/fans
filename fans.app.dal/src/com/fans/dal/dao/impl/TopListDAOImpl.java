package com.fans.dal.dao.impl;

import java.util.Date;
import java.util.Map;

import com.fans.dal.dao.TopListDAO;
import com.fans.dal.model.TopListDO;
import com.fans.dal.query.TopListQueryCondition;
import com.google.common.collect.Maps;
import com.victor.framework.dal.basic.EntityDAO;

public class TopListDAOImpl extends EntityDAO<TopListDO,TopListQueryCondition> implements TopListDAO{

	public TopListDAOImpl() {
		super(TopListDO.class.getSimpleName());
	}

	@Override
	public void expire(Long userId) {
		TopListDO forUpdate = new TopListDO();
		forUpdate.setUserId(userId);
		super.updateBySID("expire", forUpdate);
	}

	@Override
	public void extend(Long id, Integer position, Date gmtEnd) {
		TopListDO forUpdate = new TopListDO();
		forUpdate.setId(id);
		forUpdate.setPosition(position);
		forUpdate.setGmtEnd(gmtEnd);
		super.updateBySID("extend", forUpdate);
		
	}

	@Override
	public TopListDO getValidByUserId(Long userId, Integer position) {
		Map<String,Object> query = Maps.newHashMap();
		query.put("userId", userId);
		query.put("position", position);
		return super.queryForEntity("getValidByUserId", query);
	}
}
