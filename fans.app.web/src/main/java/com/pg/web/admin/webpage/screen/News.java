package com.pg.web.admin.webpage.screen;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.pg.dal.cache.NewsCache;
import com.pg.dal.enumerate.BooleanEnum;
import com.pg.dal.enumerate.NewsTypeEnum;
import com.pg.dal.enumerate.ResourceEnum;
import com.pg.web.admin.model.json.CrumbJson;

public class News {
	
	@Autowired
	private NewsCache newsCache;
	
	public void execute(Context context){
		setCrumb(context);
		context.put("list", JSONObject.toJSONString(newsCache.cacheValues()));
		context.put("typeEnum", NewsTypeEnum.getAll());
		context.put("booleanEnum", BooleanEnum.getAll());
	}
	
	private void setCrumb(Context context){
		List<CrumbJson> crumbs = Lists.newLinkedList();
		crumbs.add(new CrumbJson(ResourceEnum.系统通知.getName(),ResourceEnum.系统通知.getUri()));
		context.put("crumbs", crumbs);
	}
}
