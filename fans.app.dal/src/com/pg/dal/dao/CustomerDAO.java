package com.pg.dal.dao;

import java.util.List;

import com.pg.dal.model.CustomerDO;
import com.pg.dal.query.CustomerQueryCondition;

public interface CustomerDAO {
	/**
	 * 创建对象
	 * @param customerDO
	 * @return
	 */
	Long insert(CustomerDO customerDO);
	
	/**
	 * 更新对象信息
	 * @param customerDO
	 */
	Boolean update(CustomerDO customerDO);
	
	/**
	 * 根据ID获取
	 * @param id
	 * @return
	 */
	CustomerDO getById(Long id);
	
	/**
	 * 根据用户名和密码获取
	 * @param customerDO
	 * @return
	 */
	CustomerDO login(String mobile, String password);

	/**
	 * 根据查询条件获取
	 * @param queryCondition
	 * @return
	 */
	List<CustomerDO> getByCondition(CustomerQueryCondition queryCondition);
	
	/**
	 * 获取分页数据
	 * @param queryCondition
	 * @return
	 */
	List<CustomerDO> getPage(CustomerQueryCondition queryCondition);
	Integer getCount(CustomerQueryCondition queryCondition);
}
