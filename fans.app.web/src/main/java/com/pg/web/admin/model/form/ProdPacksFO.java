package com.pg.web.admin.model.form;

import java.util.List;

import com.google.common.collect.Lists;
import com.pg.dal.model.ProdPackDO;
import com.victor.framework.common.shared.Split;

public class ProdPacksFO {
	private Long packageId;
	private String productIds;
	public Long getPackageId() {
		return packageId;
	}
	public void setPackageId(Long packageId) {
		this.packageId = packageId;
	}
	public String getProductIds() {
		return productIds;
	}
	public void setProductIds(String productIds) {
		this.productIds = productIds;
	}
	public List<ProdPackDO> getDO(){
		List<ProdPackDO> list = Lists.newArrayList();
		String[] productIdArray = productIds.split(Split.逗号);
		int min = productIdArray.length;
		for(int i=0;i<min;i++){
			try {
				Long productId = Long.parseLong(productIdArray[i]);
				ProdPackDO prodPackDO = new ProdPackDO();
				prodPackDO.setPackageId(packageId);
				prodPackDO.setProductId(productId);
				list.add(prodPackDO);
			} catch (Exception e) {
				// do nothing
			}
		}
		return list;
	}
}
