package com.pg.dal.dao.impl;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.pg.dal.dao.ProductDAO;
import com.pg.dal.model.ProductDO;
import com.pg.dal.query.ProductQueryCondition;
import com.victor.framework.dal.basic.EntityDAO;

public class ProductDAOImpl extends EntityDAO<ProductDO,ProductQueryCondition> implements ProductDAO{

	public ProductDAOImpl() {
		super(ProductDO.class.getSimpleName());
	}
	
	public Map<String,String> getEnumMap(){
		Map<String,String> map = Maps.newLinkedHashMap();
		List<ProductDO> list = this.getByCondition(new ProductQueryCondition());
		for(ProductDO e: list){
			map.put(getEnumKey(e), getEnumValue(e));
		}
		return map;
	}

}
