package com.pg.dal.model;

import java.io.Serializable;
import java.util.Map;

import com.google.common.collect.Maps;
import com.pg.dal.enumerate.EnableEnum;
import com.pg.dal.query.ProductQueryCondition;
import com.victor.framework.annotation.EnumValue;
import com.victor.framework.common.tools.JsonTools;
import com.victor.framework.common.tools.StringTools;
import com.victor.framework.dal.basic.EntityDO;

/**
 * 产品
 * @author victorhan
 *
 */
public class ProductDO extends EntityDO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 923264648570038956L;
	
	@EnumValue
	private String name;		  //名称
	private String title;		  //标题
	private Long brandId;		  //品牌ID
	private Long categoryId;	  //品类ID
	private Double su;		      //盈利共享度/箱
	private Double price3500;	  //3500箱价格
	private Double price2000;	  //2000箱价格
	private Double price800;	  //800箱价格
	private Double price200NoTax; //200箱无税价格
	private Double price200;	  //200箱价格
	private Double price100;	  //100箱价格
	private Double priceSugg;	  //建议售价
	private Double cubage;		  //立方分米/体积
	private Double weight;		  //公斤/箱
	private Integer volume;       //库存
	private String properties;    //属性
	private String enable;		  //有效
	
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
	public Double getSu() {
        return su;
    }
    public void setSu(Double su) {
        this.su = su;
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
	public String getEnable() {
		return enable;
	}
	public void setEnable(String enable) {
		this.enable = enable;
	}
	public Double getPrice3500() {
		return price3500;
	}
	public void setPrice3500(Double price3500) {
		this.price3500 = price3500;
	}
	public Double getPrice2000() {
		return price2000;
	}
	public void setPrice2000(Double price2000) {
		this.price2000 = price2000;
	}
	public Double getPrice800() {
		return price800;
	}
	public void setPrice800(Double price800) {
		this.price800 = price800;
	}
	public Double getPrice200NoTax() {
        return price200NoTax;
    }
    public void setPrice200NoTax(Double price200NoTax) {
        this.price200NoTax = price200NoTax;
    }
    public String getProperties() {
        return properties;
    }
    public void setProperties(String properties) {
        this.properties = properties;
    }
    public Double getPrice200() {
		return price200;
	}
	public void setPrice200(Double price200) {
		this.price200 = price200;
	}
	public Double getPrice100() {
		return price100;
	}
	public void setPrice100(Double price100) {
		this.price100 = price100;
	}
	public Double getPriceSugg() {
		return priceSugg;
	}
	public void setPriceSugg(Double priceSugg) {
		this.priceSugg = priceSugg;
	}
	public Double getCubage() {
		return cubage;
	}
	public void setCubage(Double cubage) {
		this.cubage = cubage;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	
	public Integer getVolume() {
        return volume;
    }
    public void setVolume(Integer volume) {
        this.volume = volume;
    }
    @SuppressWarnings("unchecked")
    public Map<String,String> getPropMap(){
	    if(StringTools.isEmpty(properties)){
	        return Maps.newHashMap();
	    }
	    Map<String,String> map;
        try {
            map = JsonTools.fromJson(properties, Map.class);
        } catch (Exception e) {
            return Maps.newHashMap();
        }
	    return map;
	}
	
	public boolean isValid(){
		if(StringTools.isEmpty(enable)){
			return false;
		}
		if(EnableEnum.无效.getCode().equals(enable)){
			return false;
		}
		if(volume == null || volume <= 0){
		    return false;
		}
		return true;
	}
	
	public ProductQueryCondition toQueryCondition(){
		ProductQueryCondition queryCondition = new ProductQueryCondition();
		queryCondition.setQueryMap(this.toMap());
		return queryCondition;
	}
}
