package com.pg.dal.enumerate;

import java.util.List;

import com.google.common.collect.Lists;

public enum NewsTypeEnum {
	活动("活动","活动"),
	重要("重要","重要"),
	会议("会议","会议"),
	注意("注意","注意"),
	系统("系统","系统"),
	通知("通知","通知");
	
	private String code;
	private String desc;
	
	private NewsTypeEnum(String code,String desc){
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	public static List<NewsTypeEnum> getAll(){
		return Lists.newArrayList(NewsTypeEnum.values());
	}
	
	public static NewsTypeEnum getByCode(String code){
		for(NewsTypeEnum news : getAll()){
			if(news.code.equals(code)){
				return news;
			}
		}
		return null;
	}
	
	public static NewsTypeEnum getByDesc(String desc){
		for(NewsTypeEnum news : getAll()){
			if(news.desc.equals(desc)){
				return news;
			}
		}
		return null;
	}
	
	public static NewsTypeEnum getByText(String text){
		if(getByCode(text) == null) {
			return getByDesc(text);
		}
		return getByCode(text);
	}
}
