package com.pg.dal.dao;

import java.util.List;
import java.util.Map;

import com.pg.dal.model.PublishDO;
import com.pg.dal.query.PublishQueryCondition;

public interface PublishDAO {
	/**
	 * 创建对象
	 * @param PublishDO
	 * @return
	 */
	Long insert(PublishDO publishDO);
	
	/**
	 * 更新对象信息
	 * @param PublishDO
	 */
	Boolean update(PublishDO publishDO);
	
	/**
	 * 删除对象
	 * @param id
	 */
	/**
	 * 软删除
	 * @param id
	 * @return
	 */
	Boolean softDelete(Long id);
	Boolean softDeleteByPackageId(Long packageId);
	
	Boolean recover(Long id);
	
	/**
	 * 根据ID获取
	 * @param id
	 * @return
	 */
	PublishDO getById(Long id);
	
	/**
	 * 
	 * @return
	 */
	Map<String,String> getEnumMap();

	/**
	 * 根据查询条件获取
	 * @param queryCondition
	 * @return
	 */
	List<PublishDO> getByCondition(PublishQueryCondition queryCondition);
	
	/**
	 * 获取分页数据
	 * @param queryCondition
	 * @return
	 */
	List<PublishDO> getPage(PublishQueryCondition queryCondition);
	Integer getCount(PublishQueryCondition queryCondition);
}
