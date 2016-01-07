package com.fans.web.admin.screen;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.fastjson.JSONObject;
import com.fans.admin.model.json.CrumbJson;
import com.fans.dal.cache.SystemConfigCache;
import com.fans.dal.enumerate.ResourceEnum;
import com.fans.dal.model.SystemConfigDO;
import com.google.common.collect.Lists;

public class System {
	
	@Autowired
	private SystemConfigCache systemConfigCache;
	
	public void execute(@Param(name="reload", defaultValue="false")boolean reload,Context context){
		setCrumb(context);
		if(reload){
			systemConfigCache.reload();
		}
		List<SystemConfigDO> list = systemConfigCache.cacheValues();
		context.put("list", JSONObject.toJSONString(list));
	}
	
	private void setCrumb(Context context){
		List<CrumbJson> crumbs = Lists.newLinkedList();
		crumbs.add(new CrumbJson(ResourceEnum.系统配置.getName(),ResourceEnum.系统配置.getUri()));
		context.put("crumbs", crumbs);
	}
}
