package com.pg.dal.model;

import java.io.Serializable;

import com.pg.dal.query.PurchaseItemQueryCondition;
import com.victor.framework.annotation.EnumValue;
import com.victor.framework.dal.basic.EntityDO;

/**
 * 订单商品
 * @author victorhan
 *
 */
public class PurchaseItemDO extends EntityDO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 923264648570038956L;
	
	@EnumValue
	private String 	name;			//名称
	private String 	title;			//标题
	private Long 	brandId;		//品牌ID
	private Long 	categoryId;		//品类ID
	private Double 	msu;			//MSU
	private Double 	price;			//成交价格
	private Integer quantity;		//数量
	private Double 	mcubage;		//体积 (立方米)
	private Double 	mweight;		//重量 (吨)
	private Long    purchaseId;     //订单购买ID
    private Long    productId;      //产品ID
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getBrandId() {
		return brandId;
	}

	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Double getMsu() {
		return msu;
	}

	public void setMsu(Double msu) {
		this.msu = msu;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getMcubage() {
		return mcubage;
	}

	public void setMcubage(Double mcubage) {
		this.mcubage = mcubage;
	}

	public Double getMweight() {
		return mweight;
	}

	public void setMweight(Double mweight) {
		this.mweight = mweight;
	}

	public Long getPurchaseId() {
		return purchaseId;
	}

	public void setPurchaseId(Long purchaseId) {
		this.purchaseId = purchaseId;
	}
	
	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
    public PurchaseItemQueryCondition toQueryCondition(){
		PurchaseItemQueryCondition queryCondition = new PurchaseItemQueryCondition();
		queryCondition.setQueryMap(this.toMap());
		return queryCondition;
	}
}
