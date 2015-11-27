package com.pg.dal.dao;

import java.util.List;

import com.pg.dal.model.OrderDO;
import com.pg.dal.query.OrderQueryCondition;

public interface OrderDAO {
	/**
	 * 创建对象
	 * @param OrderDO
	 * @return
	 */
	Long insert(OrderDO orderDO);
	
	/**
	 * 更新对象信息
	 * @param OrderDO
	 */
	Boolean update(OrderDO orderDO);
	
	/**
	 * 根据ID获取
	 * @param id
	 * @return
	 */
	OrderDO getById(Long id);

	/**
	 * 根据查询条件获取
	 * @param queryCondition
	 * @return
	 */
	List<OrderDO> getByCondition(OrderQueryCondition queryCondition);
	
	/**
	 * 查询总销售额
	 * @param queryCondition
	 * @return
	 */
	Double getTotalSale(OrderQueryCondition queryCondition);
	
	/**
	 * 查询总定金
	 * @param queryCondition
	 * @return
	 */
	Double getTotalDeposit(OrderQueryCondition queryCondition);
	/**
	 * 获取分页数据
	 * @param queryCondition
	 * @return
	 */
	List<OrderDO> getPage(OrderQueryCondition queryCondition);
	Integer getCount(OrderQueryCondition queryCondition);
}
