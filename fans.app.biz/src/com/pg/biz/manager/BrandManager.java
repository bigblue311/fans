package com.pg.biz.manager;

import java.util.List;
import java.util.Map;

import com.pg.dal.model.BrandDO;

public interface BrandManager {
	
	/**
	 * 创建一个品牌
	 * @param brandDO
	 */
	void create(BrandDO brandDO);
	
	/**
	 * 更新一个品牌
	 * @param brandDO
	 */
	void update(BrandDO brandDO);
	
	/**
	 * 删除一个品牌
	 * 会遍历的删除子节点
	 * @param id
	 */
	void delete(Long id);
	
	/**
	 * 根据ID获取
	 * @param id
	 * @return
	 */
	BrandDO getById(Long id);
	
	/**
	 * 获取枚举MAP
	 * @return
	 */
	Map<String,String> getEnumMap();
	
	/**
	 * 获取父节点所有品牌
	 * @return
	 */
	List<BrandDO> getTopLevel();
	
	/**
	 * 根据父节点ID获取子节点
	 * @param parentId
	 * @return
	 */
	List<BrandDO> getLevel(Long parentId);
}
