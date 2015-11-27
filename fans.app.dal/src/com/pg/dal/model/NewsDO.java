package com.pg.dal.model;

import java.io.Serializable;

import com.victor.framework.dal.basic.EntityDO;

/**
 * 系统消息
 * @author victorhan
 *
 */
public class NewsDO extends EntityDO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5187342639075908283L;
	private String news;
	private String type;
	private String top;
	
	public String getNews() {
		return news;
	}
	public void setNews(String news) {
		this.news = news;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTop() {
		return top;
	}
	public void setTop(String top) {
		this.top = top;
	}
}
