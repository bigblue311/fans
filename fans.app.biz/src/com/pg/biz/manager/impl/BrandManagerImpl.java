package com.pg.biz.manager.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.pg.biz.manager.BrandManager;
import com.pg.dal.dao.BrandDAO;
import com.pg.dal.model.BrandDO;
import com.pg.dal.query.BrandQueryCondition;

public class BrandManagerImpl implements BrandManager{

	@Autowired
	private BrandDAO brandDAO;
	
	@Override
	public void create(BrandDO brandDO) {
		brandDAO.insert(brandDO);
	}

	@Override
	public void update(BrandDO brandDO) {
		brandDAO.update(brandDO);
	}

	@Override
	public void delete(Long id) {
		//遍历的删除子节点
		List<BrandDO> sonList = getLevel(id);
		for(BrandDO brandDO : sonList){
			if(brandDO != null){
				delete(brandDO.getId());
			}
		}
		//再删除自己
		brandDAO.softDelete(id);
	}

	@Override
	public BrandDO getById(Long id) {
		return brandDAO.getById(id);
	}

	@Override
	public List<BrandDO> getTopLevel() {
		BrandQueryCondition queryCondition = new BrandQueryCondition();
		queryCondition.setParentId(0l);
		return brandDAO.getByCondition(queryCondition);
	}

	@Override
	public List<BrandDO> getLevel(Long parentId) {
		BrandQueryCondition queryCondition = new BrandQueryCondition();
		queryCondition.setParentId(parentId);
		return brandDAO.getByCondition(queryCondition);
	}

	@Override
	public Map<String, String> getEnumMap() {
		return brandDAO.getEnumMap();
	}

}
