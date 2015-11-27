package com.pg.web.admin.model.form;

import com.pg.dal.model.ProductDO;

public class ProductFO extends ProductDO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1005217326698159014L;
	
	public ProductDO getDO(){
		ProductDO productDO = new ProductDO();
		productDO.setId(getId());
		productDO.setName(getName());
		productDO.setTitle(getTitle());
		productDO.setBrandId(getBrandId());
		productDO.setCategoryId(getCategoryId());
		productDO.setSu(getSu());
		productDO.setPrice3500(getPrice3500());
		productDO.setPrice2000(getPrice2000());
		productDO.setPrice800(getPrice800());
		productDO.setPrice200NoTax(getPrice200NoTax());
		productDO.setPrice200(getPrice200());
		productDO.setPrice100(getPrice100());
		productDO.setPriceSugg(getPriceSugg());
		productDO.setCubage(getCubage());
		productDO.setWeight(getWeight());
		productDO.setVolume(getVolume());
		productDO.setEnable(getEnable());
		return productDO;
	}
}
