package com.pg.dal.cache;

import java.util.List;
import java.util.Map;

import com.pg.dal.model.RoleDO;

public interface RoleCache {
	/**
	 * 获取所有
	 * @return
	 */
	List<RoleDO> cacheValues();
	
	/**
	 * 获得枚举方法
	 * @return
	 */
	Map<String,String> getEnumMap();
	
	/**
	 * 重新加载
	 */
	void reload();
	
	/**
	 * 判断是否存在
	 * @param id
	 * @return
	 */
	boolean exist(Long id);
	
	/**
	 * 获取缓存对象
	 * @param key
	 * @return
	 */
	RoleDO getCache(String key);
	
	/**
	 * 插入数据库
	 * @param roleDO
	 */
	void insertDB(RoleDO roleDO);
	
	/**
	 * 更新数据库
	 * @param roleDO
	 */
	void updateDB(RoleDO roleDO);
	
	/**
	 * 删除数据库
	 * @param id
	 */
	void deleteDB(Long id);
}
