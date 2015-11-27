package com.pg.dal.model;

import java.io.Serializable;

import com.pg.dal.enumerate.EnableEnum;
import com.pg.dal.query.CategoryQueryCondition;
import com.victor.framework.annotation.EnumValue;
import com.victor.framework.common.tools.StringTools;
import com.victor.framework.dal.basic.EntityDO;

/**
 * 品类
 * @author victorhan
 *
 */
public class CategoryDO extends EntityDO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5180648161565465644L;
	
	@EnumValue
	private String name;		//名称
	private Long parentId;		//父节点ID
	private String enable;		//是否有效
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getEnable() {
		return enable;
	}
	public void setEnable(String enable) {
		this.enable = enable;
	}
	
	public boolean isValid(){
		if(StringTools.isEmpty(enable)){
			return false;
		}
		if(EnableEnum.无效.getCode().equals(enable)){
			return false;
		}
		return true;
	}
	
	public CategoryQueryCondition toQueryCondition(){
		CategoryQueryCondition queryCondition = new CategoryQueryCondition();
		queryCondition.setQueryMap(this.toMap());
		return queryCondition;
	}
}
