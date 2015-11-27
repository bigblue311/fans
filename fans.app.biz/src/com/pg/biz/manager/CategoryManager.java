package com.pg.biz.manager;

import java.util.List;
import java.util.Map;

import com.pg.dal.model.CategoryDO;

public interface CategoryManager {
	
	/**
	 * 创建一个品类
	 * @param CategoryDO
	 */
	void create(CategoryDO categoryDO);
	
	/**
	 * 更新一个品类
	 * @param CategoryDO
	 */
	void update(CategoryDO categoryDO);
	
	/**
	 * 删除一个品类
	 * 会遍历的删除子节点
	 * @param id
	 */
	void delete(Long id);
	
	/**
	 * 获取枚举MAP
	 * @return
	 */
	Map<String,String> getEnumMap();
	
	/**
	 * 根据ID获取
	 * @param id
	 * @return
	 */
	CategoryDO getById(Long id);
	
	/**
	 * 获取父节点所有品类
	 * @return
	 */
	List<CategoryDO> getTopLevel();
	
	/**
	 * 根据父节点获取所有品类
	 * @param parentId
	 * @return
	 */
	List<CategoryDO> getLevel(Long parentId);
}
