package com.fans.web.admin.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.FormGroup;
import com.fans.admin.model.form.ResourceRolesFO;
import com.fans.biz.manager.SecurityManager;
import com.fans.dal.model.ResourceRoleDO;


public class ResourceRoleAction {
	
	@Autowired
	private SecurityManager securityManager;
	
	public void doUpdate(@FormGroup("resourceRole") ResourceRolesFO resourceRolesFO) {
		List<ResourceRoleDO> list = resourceRolesFO.getDO();
		for(ResourceRoleDO resourceRoleDO : list){
			securityManager.create(resourceRoleDO);
		}
	}
	
	public void doDelete(@FormGroup("resourceRole") ResourceRolesFO resourceRolesFO) {
		List<ResourceRoleDO> list = resourceRolesFO.getDO();
		for(ResourceRoleDO resourceRoleDO : list){
			securityManager.delete(resourceRoleDO);
		}
	}
}
