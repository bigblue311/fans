package com.pg.web.admin.webpage.screen;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Params;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.pg.biz.manager.CustomerManager;
import com.pg.biz.manager.EmployeeManager;
import com.pg.dal.enumerate.ResourceEnum;
import com.pg.dal.model.CustomerDO;
import com.pg.dal.model.EmployeeDO;
import com.pg.dal.query.CustomerQueryCondition;
import com.pg.web.admin.model.json.CrumbJson;
import com.victor.framework.dal.basic.Paging;

public class Customer {
	
	@Autowired
	private CustomerManager customerManager;
	
	@Autowired
	private EmployeeManager employeeManager;
	
	public void execute(@Params CustomerQueryCondition queryCondition,
						Context context){
		setCrumb(context,queryCondition.getId());
		
		List<EmployeeDO> employeeList = employeeManager.getAll();
		Paging<CustomerDO> pageList = Paging.emptyPage();
		pageList = customerManager.getPage(queryCondition);
		
		context.put("query", queryCondition);
		context.put("paging", pageList);
		context.put("list", JSONObject.toJSONString(pageList.getData()));
		context.put("employeeEnum", employeeList);
	}
	
	private void setCrumb(Context context,Long id){
		List<CrumbJson> crumbs = Lists.newLinkedList();
		crumbs.add(new CrumbJson(ResourceEnum.客户查询.getName(),ResourceEnum.客户查询.getUri()));
		if(id != null){
			CustomerDO customerDO = customerManager.getById(id);
			if(customerDO != null){
				crumbs.add(new CrumbJson(customerDO.getName(),ResourceEnum.客户查询.getUri()+"?id="+id));
			}
		}
		context.put("crumbs", crumbs);
	}
}
