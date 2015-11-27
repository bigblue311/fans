package com.pg.biz.manager;

import java.util.List;
import java.util.Map;

import com.pg.biz.model.PackageVO;
import com.pg.dal.model.PackageDO;
import com.pg.dal.model.ProdPackDO;
import com.pg.dal.model.ProductDO;
import com.pg.dal.model.PublishDO;
import com.pg.dal.query.PackageQueryCondition;
import com.pg.dal.query.ProdPackQueryCondition;
import com.pg.dal.query.ProductQueryCondition;
import com.pg.dal.query.PublishQueryCondition;
import com.victor.framework.dal.basic.Paging;

public interface ProductManager {
	/**
	 * 创建一个商品
	 * @param productDO
	 */
	void createProduct(ProductDO productDO);
	
	/**
	 * 更新一个商品
	 * @param productDO
	 */
	void updateProduct(ProductDO productDO);
	
	/**
	 * 删除一个商品
	 * @param id
	 */
	void deleteProduct(Long id);
	void recoverProduct(Long id);
	
	/**
	 * 根据ID获取商品
	 * @param id
	 * @return
	 */
	ProductDO getProductById(Long id);
	
	/**
	 * 
	 * @return
	 */
	Map<String,String> getProductEnumMap();
	
	/**
	 * 根据PackageId获取
	 * @param packageId
	 * @return
	 */
	List<ProductDO> getProductByPackageId(Long packageId);
	List<ProductDO> getProductNotInPackageId(Long packageId);
	
	/**
	 * 根据查询条件获取
	 * @param queryCondition
	 * @return
	 */
	List<ProductDO> getProductByCondition(ProductQueryCondition queryCondition);
	
	/**
	 * 获取分页数据
	 * @param queryCondition
	 * @return
	 */
	Paging<ProductDO> getProductPage(ProductQueryCondition queryCondition);
	
	/**
	 * 创建商品包
	 * @param packageDO
	 */
	void createPackage(PackageDO packageDO);
	
	/**
	 * 更新商品包
	 * @param packageDO
	 */
	void updatePackage(PackageDO packageDO);
	
	/**
	 * 删除一个商品包
	 * @param id
	 */
	void deletePackage(Long id);
	
	/**
	 * 根据ID获取
	 * @param id
	 * @return
	 */
	PackageDO getPackageById(Long id);
	
	/**
	 * 
	 * @return
	 */
	Map<String,String> getPackageEnumMap();
	
	/**
	 * 根据查询条件获取
	 * @param packageQueryCondition
	 * @return
	 */
	List<PackageDO> getPackageByCondition(PackageQueryCondition queryCondition);
	
	/**
	 * 获取分页数据
	 * @param packageQueryCondition
	 * @return
	 */
	Paging<PackageDO> getPackagePage(PackageQueryCondition queryCondition);
	
	/**
	 * 创建产品包与产品的关系
	 * @param prodPackDO
	 */
	void createProdPack(ProdPackDO prodPackDO);
	
	/**
	 * 更新产品包与产品的关系
	 * @param prodPackDO
	 */
	void updateProdPack(ProdPackDO prodPackDO);
	void updateProdPackQuantityUnit(ProdPackDO prodPackDO);
	/**
	 * 删除产品包与产品的关系
	 * @param id
	 */
	void deleteProdPack(Long id);
	void deleteProdPack(ProdPackDO prodPackDO);
	
	/**
	 * 根据查询条件获取
	 * @param packageQueryCondition
	 * @return
	 */
	List<ProdPackDO> getProdPackByCondition(ProdPackQueryCondition queryCondition);
	/**
	 * 发布一个商品/商品包
	 * @param publishDO
	 */
	void createPublish(PublishDO publishDO);
	
	/**
	 * 更新一个商品/商品包
	 * @param publishDO
	 */
	void updatePublish(PublishDO publishDO);
	
	/**
	 * 根据ID获取
	 * @param id
	 * @return
	 */
	PublishDO getPublishById(Long id);
	PackageVO getPackageVOByPublishId(Long id);
	
	/**
	 * 
	 * @return
	 */
	Map<String,String> getPublishEnumMap();
	
	/**
	 * 删除一个商品/商品包
	 * @param id
	 */
	void deletePublish(Long id);
	void recoverPublish(Long id);
	
	/**
	 * 根据查询条件获取
	 * @param publishQueryCondition
	 * @return
	 */
	Paging<PublishDO> getPublishPage(PublishQueryCondition queryCondition);
	List<PublishDO> getPublishByCondition(PublishQueryCondition queryCondition);
	List<PackageVO> getPackageVOByCondition(PublishQueryCondition queryCondition);
	Paging<PackageVO> getPackageVOPage(PublishQueryCondition queryCondition);
}
