package com.pg.dal.model;

import java.io.Serializable;

import com.pg.dal.query.CustomerQueryCondition;
import com.victor.framework.dal.basic.EntityDO;

/**
 * 客户
 * @author victorhan
 *
 */
public class CustomerDO extends EntityDO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1552320202480467338L;
	
	private String mobile;		//电话号码
	private String password;	//密码
	private String name;		//姓名
	private String idCard;		//身份证号
	private Long employeeId;	//归属员工
	private String wechatId;	//微信ID
	private String recommender; //推荐人
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public Long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
	public String getWechatId() {
		return wechatId;
	}
	public void setWechatId(String wechatId) {
		this.wechatId = wechatId;
	}
	public String getRecommender() {
        return recommender;
    }
    public void setRecommender(String recommender) {
        this.recommender = recommender;
    }
    
    public CustomerQueryCondition toQueryCondition(){
		CustomerQueryCondition queryCondition = new CustomerQueryCondition();
		queryCondition.setQueryMap(this.toMap());
		return queryCondition;
	}
}
