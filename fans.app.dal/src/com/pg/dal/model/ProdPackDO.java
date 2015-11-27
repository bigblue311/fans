package com.pg.dal.model;

import java.io.Serializable;

import com.pg.dal.query.ProdPackQueryCondition;
import com.victor.framework.dal.basic.EntityDO;

/**
 * 商品-商品包关系
 * @author victorhan
 *
 */
public class ProdPackDO extends EntityDO implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5460823392436569813L;
	private Long packageId;		//商品包ID
	private Long productId;		//商品ID
	
	public Long getPackageId() {
		return packageId;
	}
	public void setPackageId(Long packageId) {
		this.packageId = packageId;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public ProdPackQueryCondition toQueryCondition(){
		ProdPackQueryCondition queryCondition = new ProdPackQueryCondition();
		queryCondition.setQueryMap(this.toMap());
		return queryCondition;
	}
}
