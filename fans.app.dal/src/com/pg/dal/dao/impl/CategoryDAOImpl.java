package com.pg.dal.dao.impl;

import com.pg.dal.dao.CategoryDAO;
import com.pg.dal.model.CategoryDO;
import com.pg.dal.query.CategoryQueryCondition;
import com.victor.framework.dal.basic.EntityDAO;

public class CategoryDAOImpl extends EntityDAO<CategoryDO,CategoryQueryCondition> implements CategoryDAO{

	public CategoryDAOImpl() {
		super(CategoryDO.class.getSimpleName());
	}

}
