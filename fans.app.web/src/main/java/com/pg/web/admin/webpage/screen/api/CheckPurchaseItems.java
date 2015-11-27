package com.pg.web.admin.webpage.screen.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Params;
import com.pg.biz.manager.ProductManager;
import com.pg.biz.manager.TransactionManager;
import com.pg.biz.model.PurchaseVO;
import com.pg.dal.model.ProductDO;
import com.pg.dal.model.PublishDO;
import com.pg.dal.model.PurchaseItemDO;
import com.pg.web.admin.model.form.PurchaseItemsFO;
import com.victor.framework.common.shared.Result;

public class CheckPurchaseItems {
	@Autowired
	private ProductManager productManager;
	
	@Autowired
	private TransactionManager transactionManager;
	
	public Result<Boolean> execute(@Params PurchaseItemsFO purchaseItemsFO) {
		PurchaseVO purchaseVO = transactionManager.getPurchaseVOById(purchaseItemsFO.getPurchaseId());
		PublishDO publishDO = productManager.getPublishById(purchaseItemsFO.getPublishId());
		if(purchaseVO == null || publishDO == null || !publishDO.isValid()){
			return Result.newInstance(false, "该商品已下架", false);
		}
		Integer totalQuantity = 0;
		Double totalPrice = 0d;
		
		List<PurchaseItemDO> updateList = purchaseItemsFO.getDO();
		
		for(PurchaseItemDO purchaseItemDO : purchaseVO.getItemList()){
			Integer quantity = getFinalQuantity(purchaseItemDO,updateList);
			totalQuantity += quantity;
			totalPrice += getFinalPrice(purchaseItemDO,purchaseItemsFO.getPublishId(),quantity);
		}
		
		Integer limitBuyQuantity = publishDO.getLimitBuyQuantity();
		Double limitButPrice = publishDO.getLimitBuyPrice();
		if(limitBuyQuantity != null){
			if(totalQuantity.intValue()<limitBuyQuantity.intValue()){
				return Result.newInstance(false, "低于了最小预定数量", false);
			}
		}
		
		if(limitButPrice != null){
			if(totalPrice.doubleValue()<(limitButPrice*10000d)){
				return Result.newInstance(false, "低于了最小预定金额", false);
			}
		}
		
		return Result.newInstance(true, "可以购买", true);
	}
	
	private Integer getFinalQuantity(PurchaseItemDO purchaseItemDO, List<PurchaseItemDO> updateList){
	    ProductDO productDO = productManager.getProductById(purchaseItemDO.getProductId());
	    for(PurchaseItemDO update : updateList){
			if(update.getId().equals(purchaseItemDO.getId())){
				if(update.getQuantity()!=null||update.getQuantity()>=0){
				    Integer quantity = update.getQuantity();
			        quantity = transactionManager.checkQuantity(productDO, quantity);
					return update.getQuantity();
				}
			}
		}
		Integer quantity = purchaseItemDO.getQuantity();
        quantity = transactionManager.checkQuantity(productDO, quantity);
		return quantity;
	}
	
	private Double getFinalPrice(PurchaseItemDO purchaseItemDO, Long publishId, Integer quantity){
		Double price = transactionManager.getFinalPrice(publishId, purchaseItemDO.getProductId(), quantity);
		return quantity * price;
	}
}
