package com.pg.dal.dao;

import java.util.List;
import java.util.Map;

import com.pg.dal.model.BrandDO;
import com.pg.dal.query.BrandQueryCondition;

public interface BrandDAO {
	/**
	 * 创建对象
	 * @param BrandDO
	 * @return
	 */
	Long insert(BrandDO brandDO);
	
	/**
	 * 
	 * @return
	 */
	Map<String,String> getEnumMap();
	
	/**
	 * 更新对象信息
	 * @param BrandDO
	 */
	Boolean update(BrandDO brandDO);
	
	/**
	 * 根据ID获取
	 * @param id
	 * @return
	 */
	BrandDO getById(Long id);
	
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
	List<BrandDO> getByCondition(BrandQueryCondition queryCondition);
	
	/**
	 * 获取分页数据
	 * @param queryCondition
	 * @return
	 */
	List<BrandDO> getPage(BrandQueryCondition queryCondition);
	Integer getCount(BrandQueryCondition queryCondition);
}
