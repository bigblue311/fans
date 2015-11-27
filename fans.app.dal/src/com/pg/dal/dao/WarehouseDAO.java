package com.pg.dal.dao;

import java.util.List;
import java.util.Map;

import com.pg.dal.model.WarehouseDO;
import com.pg.dal.query.WarehouseQueryCondition;

public interface WarehouseDAO {
	/**
	 * 创建对象
	 * @param WarehouseDO
	 * @return
	 */
	Long insert(WarehouseDO warehouseDO);
	
	/**
	 * 更新对象信息
	 * @param WarehouseDO
	 */
	Boolean update(WarehouseDO warehouseDO);
	
	
	/**
	 * 
	 * @return
	 */
	Map<String,String> getEnumMap(Long customerId);
	/**
	 * 根据ID获取
	 * @param id
	 * @return
	 */
	WarehouseDO getById(Long id);
	
	/**
	 * 删除对象
	 * @param id
	 */
	Boolean delete(Long id);
	
	/**
	 * 根据查询条件获取
	 * @param queryCondition
	 * @return
	 */
	List<WarehouseDO> getByCondition(WarehouseQueryCondition queryCondition);
	
	/**
	 * 获取分页数据
	 * @param queryCondition
	 * @return
	 */
	List<WarehouseDO> getPage(WarehouseQueryCondition queryCondition);
	Integer getCount(WarehouseQueryCondition queryCondition);
}
