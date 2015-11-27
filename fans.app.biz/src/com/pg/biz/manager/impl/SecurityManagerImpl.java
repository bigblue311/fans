package com.pg.biz.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.pg.biz.manager.SecurityManager;
import com.pg.dal.cache.RoleCache;
import com.pg.dal.dao.ResourceRoleDAO;
import com.pg.dal.enumerate.ResourceEnum;
import com.pg.dal.model.EmployeeDO;
import com.pg.dal.model.ResourceRoleDO;
import com.pg.dal.model.RoleDO;
import com.pg.dal.query.ResourceRoleQueryCondition;

public class SecurityManagerImpl implements SecurityManager{

	@Autowired
	private ResourceRoleDAO resourceRoleDAO;
	
	@Autowired
	private RoleCache roleCache;
	
	@Override
	public void create(ResourceRoleDO resourceRoleDO) {
		resourceRoleDAO.insert(resourceRoleDO);
	}

	@Override
	public void delete(Long id) {
		resourceRoleDAO.delete(id);
	}
	
	@Override
	public void deleteByRoleId(Long roleId) {
		resourceRoleDAO.deleteByRoleId(roleId);
	}
	
	@Override
	public void delete(ResourceRoleDO resourceRoleDO) {
		List<ResourceRoleDO> list = resourceRoleDAO.getByCondition(resourceRoleDO.toQueryCondition());
		for(ResourceRoleDO each : list){
			resourceRoleDAO.delete(each.getId());
		}
	}

	@Override
	public List<ResourceRoleDO> getByRoleId(Long roleId) {
		if(roleId == null) {
			return Lists.newArrayList();
		}
		ResourceRoleQueryCondition queryCondition = new ResourceRoleQueryCondition();
		queryCondition.setRoleId(roleId);
		return resourceRoleDAO.getByCondition(queryCondition);
	}

	@Override
	public List<ResourceRoleDO> getByResourceId(Long resourceId) {
		if(resourceId == null) {
			return Lists.newArrayList();
		}
		ResourceRoleQueryCondition queryCondition = new ResourceRoleQueryCondition();
		queryCondition.setResourceId(resourceId);
		return resourceRoleDAO.getByCondition(queryCondition);
	}

	@Override
	public boolean hasAccess(EmployeeDO employeeDO, String resource) {
		ResourceEnum resourceEnum = ResourceEnum.getByResource(resource);
		if(resourceEnum == null) {
			return false;
		}
		if(resourceEnum.getLoginRequired()){
			Long resourceId = resourceEnum.getCode();
			if(employeeDO == null) {
				return false;
			}
			Long roleId = employeeDO.getRoleId();
			if(roleId == null){
				return false;
			}
			RoleDO role = roleCache.getCache(roleId.toString());
			if(role.isSuperAdmin()){
				//默认权限,是不需要检查的
				return true;
			}
			ResourceRoleQueryCondition queryCondition = new ResourceRoleQueryCondition();
			queryCondition.setResourceId(resourceId).setRoleId(roleId);
			return !resourceRoleDAO.getByCondition(queryCondition).isEmpty();
		} else {
			return true;
		}
	}

	@Override
	public List<ResourceEnum> getInclude(Long roleId) {
		if(roleId == null){
			return Lists.newArrayList();
		}
		List<ResourceRoleDO> list = getByRoleId(roleId);
		List<ResourceEnum>	resList = Lists.newArrayList();
		for(ResourceRoleDO resourceRoleDO : list){
			if(resourceRoleDO!=null && resourceRoleDO.getResourceId()!=null){
				ResourceEnum res = ResourceEnum.getByCode(resourceRoleDO.getResourceId());
				if(res != null){
					resList.add(res);
				}
			}
		}
		return resList;
	}

	@Override
	public List<ResourceEnum> getExclude(Long roleId) {
		List<ResourceEnum> all = ResourceEnum.loginRequired();
		if(roleId == null){
			return all;
		}
		List<ResourceEnum> include = getInclude(roleId);
		List<ResourceEnum> resList = Lists.newArrayList();
		for(ResourceEnum resourceEnum : all){
			boolean find = false;
			for(ResourceEnum in : include){
				if(resourceEnum.getCode().longValue() == in.getCode().longValue()){
					find = true;
					break;
				}
			}
			if(!find){
				resList.add(resourceEnum);
			}
		}
		return resList;
	}
}
