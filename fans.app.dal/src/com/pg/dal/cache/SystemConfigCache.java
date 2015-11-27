package com.pg.dal.cache;

import java.util.List;

import com.pg.dal.model.SystemConfigDO;

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
	 * 获取开关
	 * @param name
	 * @return
	 */
	boolean getSwitch(String key);
}
