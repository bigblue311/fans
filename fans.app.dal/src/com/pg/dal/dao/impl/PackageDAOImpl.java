package com.pg.dal.dao.impl;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.pg.dal.dao.PackageDAO;
import com.pg.dal.model.PackageDO;
import com.pg.dal.query.PackageQueryCondition;
import com.victor.framework.dal.basic.EntityDAO;

public class PackageDAOImpl extends EntityDAO<PackageDO,PackageQueryCondition> implements PackageDAO{

	public PackageDAOImpl() {
		super(PackageDO.class.getSimpleName());
	}

	
	public Map<String,String> getEnumMap(){
		Map<String,String> map = Maps.newLinkedHashMap();
		List<PackageDO> list = this.getByCondition(new PackageQueryCondition());
		for(PackageDO e: list){
			map.put(getEnumKey(e), getEnumValue(e));
		}
		return map;
	}
}
