package com.pg.biz.manager;

import java.util.List;
import java.util.Map;

import com.pg.dal.model.WarehouseDO;
import com.pg.dal.query.WarehouseQueryCondition;
import com.victor.framework.dal.basic.Paging;

public interface WarehouseManager {
	
	/**
	 * 创建一个仓库
	 * @param warehouseDO
	 */
	void create(WarehouseDO warehouseDO);
	
	/**
	 * 更新一个仓库
	 * @param warehouseDO
	 */
	void update(WarehouseDO warehouseDO);
	
	/**
	 * 删除一个仓库
	 * @param id
	 */
	void delete(Long id);
	
	/**
	 * 根据ID获取
	 * @param id
	 * @return
	 */
	WarehouseDO getById(Long id);
	
	/**
	 * 根据warehouseDO获取全地址
	 * @param warehouseDO
	 * @return
	 */
	String getProvince(WarehouseDO warehouseDO);
	String getCity(WarehouseDO warehouseDO);
	String getTown(WarehouseDO warehouseDO);
	String getFullAddress(WarehouseDO warehouseDO);
	
	/**
	 * 
	 * @return
	 */
	Map<String,String> getEnumMap(Long customerId);
	
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
	Paging<WarehouseDO> getPage(WarehouseQueryCondition queryCondition);
}
