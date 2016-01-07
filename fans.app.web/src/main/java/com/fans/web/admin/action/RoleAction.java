package com.fans.web.admin.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.FormGroup;
import com.fans.biz.manager.SecurityManager;
import com.fans.dal.cache.RoleCache;
import com.fans.dal.enumerate.EnableEnum;
import com.fans.dal.model.RoleDO;

public class RoleAction {
	@Autowired
	private RoleCache roleCache;
	@Autowired
	private SecurityManager securityManager;
	
	public void doUpdate(@FormGroup("role") RoleDO roleDO) {
		if(roleDO.getId()!=null){
			roleCache.updateDB(roleDO);
		} else {
			roleDO.setEditable(EnableEnum.有效.getCode());
			roleCache.insertDB(roleDO);
		}
	}
	
	public void doDelete(@FormGroup("role") RoleDO roleDO) {
		roleCache.deleteDB(roleDO.getId());
		securityManager.deleteByRoleId(roleDO.getId());
	}
}
