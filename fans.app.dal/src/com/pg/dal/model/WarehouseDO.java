package com.pg.dal.model;

import java.io.Serializable;

import com.pg.dal.query.WarehouseQueryCondition;
import com.victor.framework.annotation.EnumValue;
import com.victor.framework.dal.basic.EntityDO;

/**
 * 仓库
 * @author victorhan
 *
 */
public class WarehouseDO extends EntityDO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -690583555143238541L;
	
	@EnumValue
	private String 	name;			//名称
	private String 	address;		//地址
	private String 	keeper;			//收件人
	private String 	keeperIdCard;	//收件人身份证
	private String 	mobile;			//手机号码
	private String 	phone;			//联系电话
	private Long 	customerId;		//客户ID
	private String 	comment;		//备注
	private String 	province;		//省
	private String 	city;			//市
	private String 	town;			//区
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getKeeper() {
		return keeper;
	}
	public void setKeeper(String keeper) {
		this.keeper = keeper;
	}
	public String getKeeperIdCard() {
		return keeperIdCard;
	}
	public void setKeeperIdCard(String keeperIdCard) {
		this.keeperIdCard = keeperIdCard;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getTown() {
		return town;
	}
	public void setTown(String town) {
		this.town = town;
	}
	public WarehouseQueryCondition toQueryCondition(){
		WarehouseQueryCondition queryCondition = new WarehouseQueryCondition();
		queryCondition.setQueryMap(this.toMap());
		return queryCondition;
	}
}
