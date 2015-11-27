package com.pg.dal.cache;

import java.util.List;

import com.pg.dal.model.NewsDO;

public interface NewsCache {
	
	/**
	 * 获取所有
	 * @return
	 */
	List<NewsDO> cacheValues();
	
	/**
	 * 重新加载
	 */
	void reload();
	
	/**
	 * 更新缓存
	 * @param newsDO
	 */
	void insertDB(NewsDO newsDO);
	/**
	 * 更新缓存
	 * @param newsDO
	 */
	void updateDB(NewsDO newsDO);
	/**
	 * 删除缓存
	 * @param id
	 */
	void deleteDB(Long id);
}
