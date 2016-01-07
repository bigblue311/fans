package com.fans.dal.model;

import java.io.Serializable;

import com.fans.dal.query.ResourceRoleQueryCondition;
import com.victor.framework.dal.basic.EntityDO;

public class ResourceRoleDO extends EntityDO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1424603461315894634L;
	private Long roleId;
	private Long resourceId;
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public Long getResourceId() {
		return resourceId;
	}
	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}
	
	public ResourceRoleQueryCondition toQueryCondition(){
		ResourceRoleQueryCondition queryCondition = new ResourceRoleQueryCondition();
		queryCondition.setQueryMap(this.toMap());
		return queryCondition;
	}
}
