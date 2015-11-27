package com.pg.web.admin.webpage.screen;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.pg.dal.cache.RoleCache;
import com.pg.dal.enumerate.ResourceEnum;
import com.pg.dal.model.RoleDO;
import com.pg.web.admin.model.json.CrumbJson;

public class Role {
	
	@Autowired
	private RoleCache roleCache;
	
	public void execute(@Param(name="reload", defaultValue="false")boolean reload,Context context) {
		setCrumb(context);
		if(reload){
			roleCache.reload();
		}
		List<RoleDO> list = roleCache.cacheValues();
		context.put("list", JSONObject.toJSONString(list));
	}
	
	private void setCrumb(Context context){
		List<CrumbJson> crumbs = Lists.newLinkedList();
		crumbs.add(new CrumbJson(ResourceEnum.角色权限.getName(),ResourceEnum.角色权限.getUri()));
		context.put("crumbs", crumbs);
	}
}
