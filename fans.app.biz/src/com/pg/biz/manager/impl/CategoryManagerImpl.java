package com.pg.biz.manager.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.pg.biz.manager.CategoryManager;
import com.pg.dal.dao.CategoryDAO;
import com.pg.dal.model.CategoryDO;
import com.pg.dal.query.CategoryQueryCondition;

public class CategoryManagerImpl implements CategoryManager{

	@Autowired
	private CategoryDAO categoryDAO;
	
	@Override
	public void create(CategoryDO CategoryDO) {
		categoryDAO.insert(CategoryDO);
	}

	@Override
	public void update(CategoryDO CategoryDO) {
		categoryDAO.update(CategoryDO);
	}

	@Override
	public void delete(Long id) {
		//遍历的删除子节点
		List<CategoryDO> sonList = getLevel(id);
		for(CategoryDO categoryDO : sonList){
			if(categoryDO != null){
				delete(categoryDO.getId());
			}
		}
		//再删除自己
		categoryDAO.softDelete(id);
	}

	@Override
	public CategoryDO getById(Long id) {
		return categoryDAO.getById(id);
	}

	@Override
	public List<CategoryDO> getTopLevel() {
		CategoryQueryCondition queryCondition = new CategoryQueryCondition();
		queryCondition.setParentId(0l);
		return categoryDAO.getByCondition(queryCondition);
	}

	@Override
	public List<CategoryDO> getLevel(Long parentId) {
		CategoryQueryCondition queryCondition = new CategoryQueryCondition();
		queryCondition.setParentId(parentId);
		return categoryDAO.getByCondition(queryCondition);
	}

	@Override
	public Map<String, String> getEnumMap() {
		return categoryDAO.getEnumMap();
	}

}
