package com.pg.dal.dao;

import java.util.List;

import com.pg.dal.model.PurchaseItemDO;
import com.pg.dal.query.PurchaseItemQueryCondition;

public interface PurchaseItemDAO {
	/**
	 * 创建对象
	 * @param PurchaseDO
	 * @return
	 */
	Long insert(PurchaseItemDO purchaseItemDO);
	
	/**
	 * 更新对象信息
	 * @param PurchaseDO
	 */
	Boolean update(PurchaseItemDO purchaseItemDO);
	
	/**
	 * 删除对象
	 * @param id
	 */
	Boolean delete(Long id);
	
	/**
	 * 根据ID获取
	 * @param id
	 * @return
	 */
	PurchaseItemDO getById(Long id);

	/**
	 * 根据查询条件获取
	 * @param queryCondition
	 * @return
	 */
	List<PurchaseItemDO> getByCondition(PurchaseItemQueryCondition queryCondition);
	
	/**
	 * 获取分页数据
	 * @param queryCondition
	 * @return
	 */
	List<PurchaseItemDO> getPage(PurchaseItemQueryCondition queryCondition);
	Integer getCount(PurchaseItemQueryCondition queryCondition);
}
