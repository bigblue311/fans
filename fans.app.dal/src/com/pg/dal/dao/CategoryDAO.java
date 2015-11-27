package com.pg.dal.dao;

import java.util.List;
import java.util.Map;

import com.pg.dal.model.CategoryDO;
import com.pg.dal.query.CategoryQueryCondition;

public interface CategoryDAO {
	/**
	 * 创建对象
	 * @param CategoryDO
	 * @return
	 */
	Long insert(CategoryDO categoryDO);
	
	/**
	 * 
	 * @return
	 */
	Map<String,String> getEnumMap();
	
	/**
	 * 更新对象信息
	 * @param CategoryDO
	 */
	Boolean update(CategoryDO categoryDO);
	
	/**
	 * 根据ID获取
	 * @param id
	 * @return
	 */
	CategoryDO getById(Long id);
	
	/**
	 * 软删除
	 * @param id
	 * @return
	 */
	Boolean softDelete(Long id);
	
	/**
	 * 软恢复
	 * @param id
	 * @return
	 */
	Boolean recover(Long id);

	/**
	 * 根据查询条件获取
	 * @param queryCondition
	 * @return
	 */
	List<CategoryDO> getByCondition(CategoryQueryCondition queryCondition);
	
	/**
	 * 获取分页数据
	 * @param queryCondition
	 * @return
	 */
	List<CategoryDO> getPage(CategoryQueryCondition queryCondition);
	Integer getCount(CategoryQueryCondition queryCondition);
}
