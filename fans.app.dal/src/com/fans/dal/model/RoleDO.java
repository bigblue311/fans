package com.fans.dal.model;

import java.io.Serializable;

import com.fans.dal.enumerate.EnableEnum;
import com.fans.dal.query.RoleQueryCondition;
import com.victor.framework.annotation.EnumValue;
import com.victor.framework.common.tools.StringTools;
import com.victor.framework.dal.basic.EntityDO;

public class RoleDO extends EntityDO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5008645085317847952L;
	
	@EnumValue
	private String name;
	private String description;
	private String editable;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getEditable() {
		return editable;
	}
	public void setEditable(String editable) {
		this.editable = editable;
	}
	
	public boolean isSuperAdmin(){
		if(StringTools.isEmpty(editable)){
			return false;
		}
		return editable.equals(EnableEnum.无效.getCode());
	}
	
	public RoleQueryCondition toQueryCondition(){
		RoleQueryCondition queryCondition = new RoleQueryCondition();
		queryCondition.setQueryMap(this.toMap());
		return queryCondition;
	}
}
