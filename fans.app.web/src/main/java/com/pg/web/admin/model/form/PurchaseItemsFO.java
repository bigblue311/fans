package com.pg.web.admin.model.form;

import java.util.List;

import com.google.common.collect.Lists;
import com.pg.dal.model.PurchaseItemDO;
import com.victor.framework.common.shared.Split;
import com.victor.framework.common.tools.StringTools;

public class PurchaseItemsFO {
	private String ids;
	private String quantitys;
	private Long orderId;
	private Long purchaseId;
	private Long publishId;
	
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getQuantitys() {
		return quantitys;
	}
	public void setQuantitys(String quantitys) {
		this.quantitys = quantitys;
	}
	
	public Long getOrderId() {
		return orderId;
	}
	
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	
	public Long getPurchaseId() {
		return purchaseId;
	}
	public void setPurchaseId(Long purchaseId) {
		this.purchaseId = purchaseId;
	}
	public Long getPublishId() {
		return publishId;
	}
	
	public void setPublishId(Long publishId) {
		this.publishId = publishId;
	}
	
	public List<PurchaseItemDO> getDO(){
		List<PurchaseItemDO> list = Lists.newArrayList();
		if(StringTools.isAnyEmpty(ids,quantitys)){
			return list;
		}
		List<String> idList = StringTools.split(ids,Split.逗号);
		List<String> quantityList = StringTools.split(quantitys,Split.逗号);
		
		for(int i=0;i<idList.size() && i<quantityList.size();i++){
			try{
				PurchaseItemDO purchaseItemDO = new PurchaseItemDO();
				Long id = Long.parseLong(idList.get(i));
				Integer quantity = Integer.parseInt(quantityList.get(i));
				purchaseItemDO.setId(id);
				purchaseItemDO.setQuantity(quantity);
				list.add(purchaseItemDO);
			} catch(Exception ex){
				continue;
			}
		}
		return list;
	}
}
