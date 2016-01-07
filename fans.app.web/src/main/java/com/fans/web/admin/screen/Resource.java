package com.fans.web.admin.screen;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.fans.admin.model.json.CrumbJson;
import com.fans.admin.model.json.ResourceJson;
import com.fans.biz.manager.SecurityManager;
import com.fans.dal.cache.RoleCache;
import com.fans.dal.enumerate.ResourceEnum;
import com.fans.dal.model.RoleDO;

public class Resource {
	
	@Autowired
	private SecurityManager securityManager;
	
	@Autowired
	private RoleCache roleCache;
	
	public void execute(@Param(name="roleId", defaultValue="1")Long roleId,
						Context context) {
		setCrumb(context,roleId);
		context.put("roleId", roleId);
		List<ResourceJson> includeList = toJson(securityManager.getInclude(roleId));
		List<ResourceJson> excludeList = toJson(securityManager.getExclude(roleId));
		context.put("includeList", includeList);
		context.put("excludeList", excludeList);
	}
	
	private List<ResourceJson> toJson(List<ResourceEnum> list){
		if(list == null || list.isEmpty()){
			return Lists.newArrayList();
		}
		return Lists.transform(list, new Function<ResourceEnum,ResourceJson>(){

			@Override
			public ResourceJson apply(ResourceEnum resourceEnum) {
				ResourceJson json = new ResourceJson();
				json.setId(resourceEnum.getCode());
				json.setName(resourceEnum.getName());
				return json;
			}
		});
	}
	
	private void setCrumb(Context context, Long roleId){
		List<CrumbJson> crumbs = Lists.newLinkedList();
		crumbs.add(new CrumbJson(ResourceEnum.角色权限.getName(),ResourceEnum.角色权限.getUri()));
		RoleDO roleDO = roleCache.getCache(roleId.toString());
		crumbs.add(new CrumbJson(roleDO.getName(),ResourceEnum.资源权限.getUri()+"?roleId="+roleId));
		context.put("crumbs", crumbs);
	}
}
