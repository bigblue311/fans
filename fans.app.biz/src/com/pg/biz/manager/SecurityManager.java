package com.pg.biz.manager;

import java.util.List;

import com.pg.dal.enumerate.ResourceEnum;
import com.pg.dal.model.EmployeeDO;
import com.pg.dal.model.ResourceRoleDO;

public interface SecurityManager {
	/**
	 * 创建一个权限资源关系
	 * @param resourceRoleDO
	 */
	void create(ResourceRoleDO resourceRoleDO);
	
	/**
	 * 删除一个权限资源关系
	 * @param id
	 */
	void delete(Long id);
	void deleteByRoleId(Long roleId);
	void delete(ResourceRoleDO resourceRoleDO);
	
	/**
	 * 根据角色ID获取
	 * @param roleId
	 * @return
	 */
	List<ResourceRoleDO> getByRoleId(Long roleId);
	
	/**
	 * 获取该角色拥有的资源
	 * @param roleId
	 * @return
	 */
	List<ResourceEnum> getInclude(Long roleId);
	
	/**
	 * 获取该角色不拥有的资源
	 * @param roleId
	 * @return
	 */
	List<ResourceEnum> getExclude(Long roleId);
	
	/**
	 * 根据资源ID获取
	 * @param resourceId
	 * @return
	 */
	List<ResourceRoleDO> getByResourceId(Long resourceId);
	
	/**
	 * 是否有访问该资源的权限
	 * @param employeeDO
	 * @param resource
	 * @return
	 */
	boolean hasAccess(EmployeeDO employeeDO, String resource);
}
