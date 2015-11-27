package com.pg.dal.dao;

import java.util.List;

import com.pg.dal.model.ProdPackDO;
import com.pg.dal.query.ProdPackQueryCondition;

public interface ProdPackDAO {
	/**
	 * 创建对象
	 * @param ProdPackDO
	 * @return
	 */
	Long insert(ProdPackDO prodPackDO);
	
	/**
	 * 更新对象信息
	 * @param ProdPackDO
	 */
	Boolean update(ProdPackDO prodPackDO);
	
	/**
	 * 删除对象
	 * @param id
	 */
	Boolean delete(Long id);
	Boolean deleteByPackageId(Long packageId);
	
	/**
	 * 根据ID获取
	 * @param id
	 * @return
	 */
	ProdPackDO getById(Long id);

	/**
	 * 根据查询条件获取
	 * @param queryCondition
	 * @return
	 */
	List<ProdPackDO> getByCondition(ProdPackQueryCondition queryCondition);
	
	/**
	 * 获取分页数据
	 * @param queryCondition
	 * @return
	 */
	List<ProdPackDO> getPage(ProdPackQueryCondition queryCondition);
	Integer getCount(ProdPackQueryCondition queryCondition);
}
