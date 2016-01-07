package com.fans.web.admin.screen;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.fastjson.JSONObject;
import com.fans.admin.model.json.CrumbJson;
import com.fans.biz.manager.EmployeeManager;
import com.fans.dal.cache.RoleCache;
import com.fans.dal.enumerate.ResourceEnum;
import com.fans.dal.model.EmployeeDO;
import com.google.common.collect.Lists;

public class Employee {
	
	@Autowired
	private EmployeeManager employeeManager;
	
	@Autowired
	private RoleCache roleCache;
	
	public void execute(Context context) {
		setCrumb(context);
		List<EmployeeDO> list = employeeManager.getAll();
		context.put("list", JSONObject.toJSONString(list));
		context.put("roleEnum", JSONObject.toJSONString(roleCache.getEnumMap()));
	}
	
	private void setCrumb(Context context){
		List<CrumbJson> crumbs = Lists.newLinkedList();
		crumbs.add(new CrumbJson(ResourceEnum.后台账号.getName(),ResourceEnum.后台账号.getUri()));
		context.put("crumbs", crumbs);
	}
}
