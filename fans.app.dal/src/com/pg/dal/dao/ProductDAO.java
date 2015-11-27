package com.pg.dal.dao;

import java.util.List;
import java.util.Map;

import com.pg.dal.model.ProductDO;
import com.pg.dal.query.ProductQueryCondition;

public interface ProductDAO {
	/**
	 * 创建对象
	 * @param ProductDO
	 * @return
	 */
	Long insert(ProductDO ProductDO);
	
	/**
	 * 更新对象信息
	 * @param ProductDO
	 */
	Boolean update(ProductDO ProductDO);
	
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
	ProductDO getById(Long id);

	/**
	 * 根据查询条件获取
	 * @param queryCondition
	 * @return
	 */
	List<ProductDO> getByCondition(ProductQueryCondition queryCondition);
	
	/**
	 * 获取分页数据
	 * @param queryCondition
	 * @return
	 */
	List<ProductDO> getPage(ProductQueryCondition queryCondition);
	Integer getCount(ProductQueryCondition queryCondition);
}
