package com.pg.web.admin.model.form;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.victor.framework.common.shared.Split;
import com.victor.framework.common.tools.StringTools;

public class OrderFO {
	private Long publishId;
	private Long warehouseId;
	private String comment;
	private String ids;
	private String quantitys;
	
	public Long getPublishId() {
		return publishId;
	}
	public void setPublishId(Long publishId) {
		this.publishId = publishId;
	}
	public Long getWarehouseId() {
		return warehouseId;
	}
	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
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
	
	public Map<Long,Integer> getProductMap(){
		Map<Long,Integer> map = Maps.newHashMap();
		if(StringTools.isAnyEmpty(ids,quantitys)){
			return map;
		}
		List<String> idList = StringTools.split(ids,Split.逗号);
		List<String> quantityList = StringTools.split(quantitys,Split.逗号);
		
		for(int i=0;i<idList.size() && i<quantityList.size();i++){
			try{
				Long id = Long.parseLong(idList.get(i));
				Integer quantity = Integer.parseInt(quantityList.get(i));
				map.put(id, quantity);
			} catch(Exception ex){
				continue;
			}
		}
		return map;
	}
}
