package com.fans.dal.model;

import java.io.Serializable;
import java.util.Date;

import com.fans.dal.query.TopListQueryCondition;
import com.victor.framework.dal.basic.EntityDO;

public class TopListDO extends EntityDO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4635261558817654105L;
	private Long userId;				//用户ID
	private String openId;				//用户OPEN_ID
	private Long skvId;                 //用户SKV_ID
	private Integer position;			//位置
	private Date gmtStart;				//开始时间
	private Date gmtEnd;				//结束时间
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public Long getSkvId() {
        return skvId;
    }
    public void setSkvId(Long skvId) {
        this.skvId = skvId;
    }
    public Integer getPosition() {
		return position;
	}
	public void setPosition(Integer position) {
		this.position = position;
	}
	public Date getGmtStart() {
		return gmtStart;
	}
	public void setGmtStart(Date gmtStart) {
		this.gmtStart = gmtStart;
	}
	public Date getGmtEnd() {
		return gmtEnd;
	}
	public void setGmtEnd(Date gmtEnd) {
		this.gmtEnd = gmtEnd;
	}
	public TopListQueryCondition toQueryCondition(){
		TopListQueryCondition queryCondition = new TopListQueryCondition();
		queryCondition.setQueryMap(this.toMap());
		return queryCondition;
	}
}
