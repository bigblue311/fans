package com.fans.dal.cache;

import java.util.List;

import com.fans.dal.model.WeixinConfigDO;

public interface WeixinConfigCache {
	
	/**
	 * 获取所有
	 * @return
	 */
	List<WeixinConfigDO> cacheValues();
	
	/**
	 * 重新加载
	 */
	void reload();
	
	/**
	 * 获取配置
	 * @param key
	 * @return
	 */
	WeixinConfigDO getCache(String key);
}
