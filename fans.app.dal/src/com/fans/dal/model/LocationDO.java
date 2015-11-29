package com.fans.dal.model;

import java.io.Serializable;

import com.victor.framework.annotation.StaticCacheKey;
import com.victor.framework.dal.basic.EntityDO;

/**
 * 全国省市县地区行政划分
 * @author victorhan
 *
 */
public class LocationDO extends EntityDO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8822793400170288856L;
	
	@StaticCacheKey
	private String province; 	//省
	@StaticCacheKey
	private String city;		//市
	@StaticCacheKey
	private String town;		//县
	
	private String name;		//名称
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
