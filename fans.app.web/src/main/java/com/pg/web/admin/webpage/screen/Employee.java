package com.pg.web.admin.webpage.screen;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.pg.biz.manager.EmployeeManager;
import com.pg.dal.cache.RoleCache;
import com.pg.dal.enumerate.ResourceEnum;
import com.pg.dal.model.EmployeeDO;
import com.pg.web.admin.model.json.CrumbJson;

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
