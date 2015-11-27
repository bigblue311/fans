package com.pg.dal.dao;

import java.util.List;

import com.pg.dal.model.PurchaseDO;
import com.pg.dal.query.PurchaseQueryCondition;

public interface PurchaseDAO {
	/**
	 * 创建对象
	 * @param PurchaseDO
	 * @return
	 */
	Long insert(PurchaseDO purchaseDO);
	
	/**
	 * 更新对象信息
	 * @param PurchaseDO
	 */
	Boolean update(PurchaseDO purchaseDO);
	
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
	PurchaseDO getById(Long id);

	/**
	 * 根据查询条件获取
	 * @param queryCondition
	 * @return
	 */
	List<PurchaseDO> getByCondition(PurchaseQueryCondition queryCondition);
	
	/**
	 * 获取分页数据
	 * @param queryCondition
	 * @return
	 */
	List<PurchaseDO> getPage(PurchaseQueryCondition queryCondition);
	Integer getCount(PurchaseQueryCondition queryCondition);
}
