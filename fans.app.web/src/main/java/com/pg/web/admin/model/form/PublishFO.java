package com.pg.web.admin.model.form;

import java.util.Date;

import com.pg.dal.model.PublishDO;
import com.victor.framework.common.tools.DateTools;

public class PublishFO extends PublishDO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8374511238537862779L;
	private String gmtValidFrom;
	private String gmtValidTo;
	public String getGmtValidFrom() {
		return gmtValidFrom;
	}
	public void setGmtValidFrom(String gmtValidFrom) {
		this.gmtValidFrom = gmtValidFrom;
		this.setValidFrom(DateTools.getDayBegin(StringToDate(gmtValidFrom)));
	}
	public String getGmtValidTo() {
		return gmtValidTo;
	}
	public void setGmtValidTo(String gmtValidTo) {
		this.gmtValidTo = gmtValidTo;
		this.setValidTo(DateTools.getDayEnd(StringToDate(gmtValidTo)));
	}
	
	protected Date StringToDate(String date){
		Date toDate = DateTools.today();
		try {
			toDate = DateTools.StringToDate(date);
		} catch (Exception e) {
			toDate = DateTools.today();
		}
		return toDate==null?DateTools.today():toDate;
	}
	
	public PublishDO getDO(){
		PublishDO publisDO = new PublishDO();
		publisDO.setId(getId());
		publisDO.setDiscount(getDiscount());
		publisDO.setWarehouseId(getWarehouseId());
		publisDO.setPackageId(getPackageId());
		publisDO.setLimitBuyQuantity(getLimitBuyQuantity());
		publisDO.setLimitBuyPrice(getLimitBuyPrice());
		publisDO.setValidFrom(getValidFrom());
		publisDO.setValidTo(getValidTo());
		publisDO.setEnable(getEnable());
		publisDO.setDescription(getDescription());
		return publisDO;
	}
}
