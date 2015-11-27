package com.pg.dal.model;

import java.io.Serializable;
import java.util.Date;

import com.pg.dal.enumerate.EnableEnum;
import com.pg.dal.query.PublishQueryCondition;
import com.victor.framework.common.tools.DateTools;
import com.victor.framework.common.tools.StringTools;
import com.victor.framework.dal.basic.EntityDO;

public class PublishDO extends EntityDO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 308922163884509394L;
	private Double 	discount;			//折扣
	private Long 	warehouseId;		//仓库ID
	private Long 	packageId;			//商品包ID
	private Integer limitBuyQuantity;	//最小预定量
	private Double	limitBuyPrice;		//最小购买金额
	private Date 	validFrom;			//有效期开始
	private Date 	validTo;			//有效期结束
	private String 	enable;				//有效
	private String 	description;		//描述
	
	
	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Long getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}

	public Long getPackageId() {
		return packageId;
	}

	public void setPackageId(Long packageId) {
		this.packageId = packageId;
	}

	public Integer getLimitBuyQuantity() {
		return limitBuyQuantity;
	}

	public void setLimitBuyQuantity(Integer limitBuyQuantity) {
		this.limitBuyQuantity = limitBuyQuantity;
	}

	public Double getLimitBuyPrice() {
		return limitBuyPrice;
	}

	public void setLimitBuyPrice(Double limitBuyPrice) {
		this.limitBuyPrice = limitBuyPrice;
	}

	public Date getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}

	public Date getValidTo() {
		return validTo;
	}

	public void setValidTo(Date validTo) {
		this.validTo = validTo;
	}

	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
    public boolean isValid(){
		if(StringTools.isEmpty(enable)){
			return false;
		}
		if(EnableEnum.无效.getCode().equals(enable)){
			return false;
		}
		if(validFrom != null && DateTools.today().before(validFrom)){
			return false;
		}
		if(validTo != null && DateTools.today().after(validTo)){
			return false;
		}
		return true;
	}
	
	public String getValidStr(){
		if(StringTools.isEmpty(enable)){
			return "无效";
		}
		if(EnableEnum.无效.getCode().equals(enable)){
			return "无效";
		}
		if(validFrom != null && DateTools.today().before(validFrom)){
			return "未开始销售";
		}
		if(validTo != null && DateTools.today().after(validTo)){
			return "销售结束";
		}
		return "有效";
	}
	
	public PublishQueryCondition toQueryCondition(){
		PublishQueryCondition queryCondition = new PublishQueryCondition();
		queryCondition.setQueryMap(this.toMap());
		return queryCondition;
	}
}
