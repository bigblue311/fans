package com.fans.dal.dao;

import java.util.List;

import com.fans.dal.model.EmployeeDO;
import com.fans.dal.query.EmployeeQueryCondition;


public interface EmployeeDAO {
	/**
	 * 创建对象
	 * @param EmployeeDO
	 * @return
	 */
	Long insert(EmployeeDO employeeDO);
	
	/**
	 * 更新对象信息
	 * @param EmployeeDO
	 */
	Boolean update(EmployeeDO employeeDO);
	
	/**
	 * 根据ID获取
	 * @param id
	 * @return
	 */
	EmployeeDO getById(Long id);
	
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
	List<EmployeeDO> getByCondition(EmployeeQueryCondition queryCondition);
	
	/**
	 * 获取分页数据
	 * @param queryCondition
	 * @return
	 */
	List<EmployeeDO> getPage(EmployeeQueryCondition queryCondition);
	Integer getCount(EmployeeQueryCondition queryCondition);
}
