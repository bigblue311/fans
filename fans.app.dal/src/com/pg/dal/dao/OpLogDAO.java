package com.pg.dal.dao;

import java.util.List;

import com.pg.dal.model.OpLogDO;
import com.pg.dal.query.OpLogQueryCondition;

public interface OpLogDAO {
	/**
	 * 创建对象
	 * @param OpLogDO
	 * @return
	 */
	Long insert(OpLogDO opLogDO);
	
	/**
	 * 根据ID获取
	 * @param id
	 * @return
	 */
	OpLogDO getById(Long id);
	
	/**
	 * 根据查询条件获取
	 * @param queryCondition
	 * @return
	 */
	List<OpLogDO> getByCondition(OpLogQueryCondition queryCondition);
	
	/**
	 * 获取分页数据
	 * @param queryCondition
	 * @return
	 */
	List<OpLogDO> getPage(OpLogQueryCondition queryCondition);
	Integer getCount(OpLogQueryCondition queryCondition);
}
