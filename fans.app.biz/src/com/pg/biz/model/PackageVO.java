package com.pg.biz.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import com.pg.dal.model.PackageDO;
import com.pg.dal.model.ProductDO;
import com.pg.dal.model.PublishDO;
import com.pg.dal.model.WarehouseDO;
import com.victor.framework.common.tools.DateTools;
import com.victor.framework.common.tools.StringTools;

public class PackageVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2847009743718445427L;
	
	private PackageDO packageDO;
	private List<ProductDO> productList;
	private PublishDO publishDO;
	private WarehouseDO warehouseDO;
	
	private String description;
	private Boolean valid;
	private String discount;
	private String validStr;
	
	public PackageDO getPackageDO() {
		return packageDO;
	}
	public void setPackageDO(PackageDO packageDO) {
		this.packageDO = packageDO;
	}
	public List<ProductDO> getProductList() {
		return productList;
	}
	public void setProductList(List<ProductDO> productList) {
		this.productList = productList;
	}
	public PublishDO getPublishDO() {
		return publishDO;
	}
	public void setPublishDO(PublishDO publishDO) {
		this.publishDO = publishDO;
	}
	public WarehouseDO getWarehouseDO() {
		return warehouseDO;
	}
	public void setWarehouseDO(WarehouseDO warehouseDO) {
		this.warehouseDO = warehouseDO;
	}
	public String getDescription() {
		if(StringTools.isNotEmpty(description)){
			return description;
		}
		if(publishDO!=null && StringTools.isNotEmpty(publishDO.getDescription())){
			return publishDO.getDescription();
		}
		if(packageDO!=null && StringTools.isNotEmpty(packageDO.getDescription())){
			return packageDO.getDescription();
		}
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setValid(Boolean valid) {
		this.valid = valid;
	}
	
	public Boolean getValid(){
		if(valid != null){
			return valid;
		}
		if(publishDO!=null && packageDO!=null){
			return publishDO.isValid() && packageDO.isValid();
		}
		return false;
	}
	public String getDiscount() {
		if(StringTools.isNotEmpty(discount) || publishDO == null){
			return discount;
		}
		if(publishDO.getDiscount()==null){
			return "全价";
		} else {
			BigDecimal dis = new BigDecimal(publishDO.getDiscount()*10d);
			dis.setScale(2, RoundingMode.HALF_UP);
			return dis.toPlainString()+"折";
		}
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public String getValidStr() {
		if(StringTools.isNotEmpty(validStr) || publishDO == null){
			return validStr;
		}
		validStr = "";
		Date validFrom = publishDO.getValidFrom();
		Date validTo = publishDO.getValidTo();
		if(validFrom == null){
			validStr += "";
		} else {
			validStr += DateTools.DateToStringSimple(validFrom) + " 至 ";
		}
		if(validTo == null){
			validStr += "";
		} else {
			validStr += DateTools.DateToStringSimple(validTo);
		}
		return validStr;
	}
	public void setValidStr(String validStr) {
		this.validStr = validStr;
	}
}
