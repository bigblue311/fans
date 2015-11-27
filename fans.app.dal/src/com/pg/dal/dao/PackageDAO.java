package com.pg.dal.dao;

import java.util.List;
import java.util.Map;

import com.pg.dal.model.PackageDO;
import com.pg.dal.query.PackageQueryCondition;

public interface PackageDAO {
	/**
	 * 创建对象
	 * @param PackageDO
	 * @return
	 */
	Long insert(PackageDO packageDO);
	
	/**
	 * 更新对象信息
	 * @param PackageDO
	 */
	Boolean update(PackageDO packageDO);
	
	/**
	 * 
	 * @return
	 */
	Map<String,String> getEnumMap();
	
	/**
	 * 软删除
	 * @param id
	 * @return
	 */
	Boolean softDelete(Long id);
	
	/**
	 * 软恢复
	 * @param id
	 * @return
	 */
	Boolean recover(Long id);
	
	/**
	 * 根据ID获取
	 * @param id
	 * @return
	 */
	PackageDO getById(Long id);

	/**
	 * 根据查询条件获取
	 * @param queryCondition
	 * @return
	 */
	List<PackageDO> getByCondition(PackageQueryCondition queryCondition);
	
	/**
	 * 获取分页数据
	 * @param queryCondition
	 * @return
	 */
	List<PackageDO> getPage(PackageQueryCondition queryCondition);
	Integer getCount(PackageQueryCondition queryCondition);
}
