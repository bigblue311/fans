package com.fans.admin.model.form;

import java.util.List;

import com.fans.dal.model.ResourceRoleDO;
import com.google.common.collect.Lists;
import com.victor.framework.common.shared.Split;
import com.victor.framework.common.tools.StringTools;

public class ResourceRolesFO {
	private Long roleId;
	private String resourceIds;
	
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public String getResourceIds() {
		return resourceIds;
	}
	public void setResourceIds(String resourceIds) {
		this.resourceIds = resourceIds;
	}
	
	public List<ResourceRoleDO> getDO(){
		List<ResourceRoleDO> list = Lists.newArrayList();
		if(StringTools.isEmpty(resourceIds)){
			return list;
		}
		String[] ids = resourceIds.split(Split.逗号);
		for(String id : ids){
			try{
				Long resId = Long.parseLong(id);
				ResourceRoleDO resourceRoleDO = new ResourceRoleDO();
				resourceRoleDO.setId(null);
				resourceRoleDO.setRoleId(roleId);
				resourceRoleDO.setResourceId(resId);
				list.add(resourceRoleDO);
			} catch(Exception ex){
				continue;
			}
		}
		return list;
	}
}
