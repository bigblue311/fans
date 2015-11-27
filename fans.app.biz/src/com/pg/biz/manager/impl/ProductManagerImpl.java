package com.pg.biz.manager.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.pg.biz.manager.ProductManager;
import com.pg.biz.model.PackageVO;
import com.pg.dal.dao.PackageDAO;
import com.pg.dal.dao.ProdPackDAO;
import com.pg.dal.dao.ProductDAO;
import com.pg.dal.dao.WarehouseDAO;
import com.pg.dal.model.PackageDO;
import com.pg.dal.model.ProdPackDO;
import com.pg.dal.model.ProductDO;
import com.pg.dal.model.PublishDO;
import com.pg.dal.model.WarehouseDO;
import com.pg.dal.dao.PublishDAO;
import com.pg.dal.query.PackageQueryCondition;
import com.pg.dal.query.ProdPackQueryCondition;
import com.pg.dal.query.ProductQueryCondition;
import com.pg.dal.query.PublishQueryCondition;
import com.victor.framework.dal.basic.Paging;

public class ProductManagerImpl implements ProductManager{

	@Autowired
	private ProductDAO productDAO;
	
	@Autowired
	private PackageDAO packageDAO;
	
	@Autowired
	private ProdPackDAO prodPackDAO;
	
	@Autowired
	private PublishDAO publishDAO;
	
	@Autowired
	private WarehouseDAO warehouseDAO;
	
	@Override
	public void createProduct(ProductDO productDO) {
		productDAO.insert(productDO);
	}

	@Override
	public void updateProduct(ProductDO productDO) {
		productDAO.update(productDO);
	}

	@Override
	public void deleteProduct(Long id) {
		productDAO.softDelete(id);
	}
	
	@Override
	public void recoverProduct(Long id) {
		productDAO.recover(id);
	}

	@Override
	public ProductDO getProductById(Long id) {
		return productDAO.getById(id);
	}

	@Override
	public List<ProductDO> getProductByPackageId(Long packageId) {
		ProductQueryCondition queryCondition = new ProductQueryCondition();
		queryCondition.setPackageId(packageId);
		return productDAO.getByCondition(queryCondition);
	}

	@Override
	public List<ProductDO> getProductNotInPackageId(Long packageId) {
		ProductQueryCondition queryCondition = new ProductQueryCondition();
		queryCondition.setNotInPackageId(packageId);
		return productDAO.getByCondition(queryCondition);
	}

	@Override
	public List<ProductDO> getProductByCondition(ProductQueryCondition queryCondition) {
		return productDAO.getByCondition(queryCondition);
	}

	@Override
	public Paging<ProductDO> getProductPage(ProductQueryCondition queryCondition) {
		int totalSize = productDAO.getCount(queryCondition);
		@SuppressWarnings("unchecked")
		Paging<ProductDO> page = queryCondition.getPaging(totalSize, 5);
		List<ProductDO> list = productDAO.getPage(queryCondition);
		page.setData(list);
		return page;
	}

	@Override
	public void createPackage(PackageDO packageDO) {
		packageDAO.insert(packageDO);
	}

	@Override
	public void updatePackage(PackageDO packageDO) {
		packageDAO.update(packageDO);
	}

	@Override
	public void deletePackage(Long id) {
		packageDAO.softDelete(id);
		publishDAO.softDeleteByPackageId(id);
	}

	@Override
	public PackageDO getPackageById(Long id) {
		return packageDAO.getById(id);
	}

	@Override
	public List<PackageDO> getPackageByCondition(PackageQueryCondition queryCondition) {
		return packageDAO.getByCondition(queryCondition);
	}

	@Override
	public Paging<PackageDO> getPackagePage(PackageQueryCondition queryCondition) {
		int totalSize = packageDAO.getCount(queryCondition);
		@SuppressWarnings("unchecked")
		Paging<PackageDO> page = queryCondition.getPaging(totalSize, 5);
		List<PackageDO> list = packageDAO.getPage(queryCondition);
		page.setData(list);
		return page;
	}

	@Override
	public void createProdPack(ProdPackDO prodPackDO) {
		prodPackDAO.insert(prodPackDO);
	}

	@Override
	public void updateProdPack(ProdPackDO prodPackDO) {
		prodPackDAO.update(prodPackDO);
	}
	
	@Override
	public void updateProdPackQuantityUnit(ProdPackDO prodPackDO) {
		ProdPackQueryCondition queryCondition = prodPackDO.toQueryCondition();
		List<ProdPackDO> list = prodPackDAO.getByCondition(queryCondition);
		for(ProdPackDO each : list){
			prodPackDO.setId(each.getId());
			prodPackDAO.update(prodPackDO);
		}
	}

	@Override
	public void deleteProdPack(Long id) {
		prodPackDAO.delete(id);
	}
	
	@Override
	public void deleteProdPack(ProdPackDO prodPackDO) {
		ProdPackQueryCondition queryCondition = prodPackDO.toQueryCondition();
		List<ProdPackDO> list = prodPackDAO.getByCondition(queryCondition);
		for(ProdPackDO each : list){
			prodPackDAO.delete(each.getId());
		}
	}
	
	@Override
	public List<ProdPackDO> getProdPackByCondition(ProdPackQueryCondition queryCondition) {
		return prodPackDAO.getByCondition(queryCondition);
	}

	@Override
	public void createPublish(PublishDO publishDO) {
		publishDAO.insert(publishDO);
	}

	@Override
	public void updatePublish(PublishDO publishDO) {
		publishDAO.update(publishDO);
	}

	@Override
	public void deletePublish(Long id) {
		publishDAO.softDelete(id);
	}
	
	@Override
	public void recoverPublish(Long id) {
		publishDAO.recover(id);
	}

	@Override
	public Paging<PackageVO> getPackageVOPage(PublishQueryCondition queryCondition) {
		int totalSize = publishDAO.getCount(queryCondition);
		@SuppressWarnings("unchecked")
		Paging<PackageVO> page = queryCondition.getPaging(totalSize, 5);
		List<PublishDO> publishList = publishDAO.getPage(queryCondition);
		List<PackageVO> packageList = Lists.transform(publishList, new Function<PublishDO,PackageVO>(){

			@Override
			public PackageVO apply(PublishDO publishDO) {
				return PackageDO2VO(publishDO);
			}
			
		});
		page.setData(packageList);
		return page;
	}
	
	@Override
	public List<PackageVO> getPackageVOByCondition(PublishQueryCondition queryCondition) {
		List<PublishDO> publishList = publishDAO.getByCondition(queryCondition);
		List<PackageVO> packageList = Lists.transform(publishList, new Function<PublishDO,PackageVO>(){

			@Override
			public PackageVO apply(PublishDO publishDO) {
				return PackageDO2VO(publishDO);
			}
			
		});
		return packageList;
	}
	
	private PackageVO PackageDO2VO(PublishDO publishDO){
		if(publishDO == null) {
			return null;
		}
		PackageVO packageVO = new PackageVO();
		packageVO.setPublishDO(publishDO);
		
		Long packageId = publishDO.getPackageId();
		PackageDO packageDO = packageDAO.getById(packageId);
		if(packageDO==null){
			return null;
		}
		packageVO.setPackageDO(packageDO);
		
		List<ProductDO> productList = getProductByPackageId(packageId);
		packageVO.setProductList(productList);
		
		Long warehouseID = publishDO.getWarehouseId();
		WarehouseDO warehouseDO = warehouseDAO.getById(warehouseID);
		if(warehouseDO == null){
			return null;
		}
		packageVO.setWarehouseDO(warehouseDO);
		return packageVO;
	}

	@Override
	public Map<String, String> getProductEnumMap() {
		return productDAO.getEnumMap();
	}

	@Override
	public Map<String, String> getPackageEnumMap() {
		return packageDAO.getEnumMap();
	}

	@Override
	public Paging<PublishDO> getPublishPage(PublishQueryCondition queryCondition) {
		int totalSize = publishDAO.getCount(queryCondition);
		@SuppressWarnings("unchecked")
		Paging<PublishDO> page = queryCondition.getPaging(totalSize, 5);
		List<PublishDO> publishList = publishDAO.getPage(queryCondition);
		page.setData(publishList);
		return page;
	}

	@Override
	public PublishDO getPublishById(Long id) {
		return publishDAO.getById(id);
	}
	
	@Override
	public PackageVO getPackageVOByPublishId(Long id) {
		PublishDO publishDO = getPublishById(id);
		return PackageDO2VO(publishDO);
	}


	@Override
	public Map<String, String> getPublishEnumMap() {
		return publishDAO.getEnumMap();
	}

	@Override
	public List<PublishDO> getPublishByCondition(PublishQueryCondition queryCondition) {
		return publishDAO.getByCondition(queryCondition);
	}
}
