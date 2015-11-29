package com.fans.dal.model;

import java.io.Serializable;

import com.victor.framework.annotation.StaticCacheKey;
import com.victor.framework.dal.basic.EntityDO;

/**
 * 系统配置
 * @author victorhan
 *
 */
public class SystemConfigDO extends EntityDO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 366539580312188554L;
	
	@StaticCacheKey
	private String configKey; 		//key
	
	private String configValue;		//value
	
	private String description;		//描述
	
	public String getConfigKey() {
		return configKey;
	}
	public void setConfigKey(String configKey) {
		this.configKey = configKey;
	}
	public String getConfigValue() {
		return configValue;
	}
	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
