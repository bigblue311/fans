package com.pg.dal.dao;

import java.util.List;

import com.pg.dal.model.ResourceRoleDO;
import com.pg.dal.query.ResourceRoleQueryCondition;

public interface ResourceRoleDAO {
	/**
	 * 创建对象
	 * @param resourceRoleDO
	 * @return
	 */
	Long insert(ResourceRoleDO resourceRoleDO);
	
	/**
	 * 更新对象信息
	 * @param resourceRoleDO
	 * @return
	 */
	Boolean update(ResourceRoleDO resourceRoleDO);
	
	/**
	 * 删除对象
	 * @param id
	 */
	Boolean delete(Long id);
	Boolean deleteByRoleId(Long roleId);
	
	/**
	 * 根据ID获取对象
	 * @param id
	 * @return
	 */
	ResourceRoleDO getById(Long id);
	
	/**
	 * 根据查询条件获取
	 * @param queryCondition
	 * @return
	 */
	List<ResourceRoleDO> getByCondition(ResourceRoleQueryCondition queryCondition);
	
	/**
	 * 获取分页数据
	 * @param queryCondition
	 * @return
	 */
	List<ResourceRoleDO> getPage(ResourceRoleQueryCondition queryCondition);
	Integer getCount(ResourceRoleQueryCondition queryCondition);
}
