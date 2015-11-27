package com.pg.dal.model;

import java.io.Serializable;

import com.pg.dal.enumerate.EnableEnum;
import com.pg.dal.query.PackageQueryCondition;
import com.victor.framework.annotation.EnumValue;
import com.victor.framework.common.tools.StringTools;
import com.victor.framework.dal.basic.EntityDO;

/**
 * 产品包
 * @author victorhan
 *
 */
public class PackageDO extends EntityDO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4950264146722336203L;
	
	@EnumValue
	private String name;		//名称
	private String title;		//标题
	private String description;	//描述
	private String enable;		//有效
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	
	public PackageQueryCondition toQueryCondition(){
		PackageQueryCondition queryCondition = new PackageQueryCondition();
		queryCondition.setQueryMap(this.toMap());
		return queryCondition;
	}
}
