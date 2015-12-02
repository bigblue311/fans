package com.fans.dal.cache;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fans.dal.model.SystemConfigDO;

public interface SystemConfigCache {
	
	/**
	 * 获取所有
	 * @return
	 */
	List<SystemConfigDO> cacheValues();
	
	/**
	 * 重新加载
	 */
	void reload();
	
	/**
	 * 获取配置
	 * @param key
	 * @return
	 */
	SystemConfigDO getCache(String key);
	
	
	
	/**
	 * 更新缓存
	 * @param systemConfigDO
	 */
	void updateDB(SystemConfigDO systemConfigDO);
	
	/**
	 * 获取配置信息, 带默认值和类型转换
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	String getCacheString(String key, String defaultValue);
    Integer getCacheInteger(String key, Integer defaultValue);
    Long getCacheLong(String key, Long defaultValue);
    Date getCacheDate(String key, Date defaultValue);
    Double getCacheDouble(String key, Double defaultValue);
    BigDecimal getCacheBigDecimal(String key, BigDecimal defaultValue);
	/**
	 * 获取开关
	 * @param name
	 * @return
	 */
	boolean getSwitch(String key);
}
