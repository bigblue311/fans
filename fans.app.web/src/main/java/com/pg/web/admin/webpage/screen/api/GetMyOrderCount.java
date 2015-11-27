package com.pg.web.admin.webpage.screen.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.pg.biz.manager.TransactionManager;
import com.pg.dal.model.CustomerDO;
import com.pg.dal.query.OrderQueryCondition;
import com.pg.web.admin.common.AuthenticationToken;
import com.victor.framework.common.shared.Result;
import com.victor.framework.common.tools.StringTools;

public class GetMyOrderCount {
	
	@Autowired
	private TransactionManager transactionManager;
	
	@Autowired
	private HttpSession session;
	
	public Result<Integer> execute(@Param("status") String status){
		CustomerDO customer = AuthenticationToken.getLoginedCustomer(session);
		if(customer == null){
			return Result.newInstance(0, "登陆已失效", false);
		}
		
		OrderQueryCondition queryCondition = new OrderQueryCondition();
		if(StringTools.isNotEmpty(status)){
			queryCondition.setStatus(status);
		}
		queryCondition.setCustomerId(customer.getId());
		Integer count = transactionManager.getOrderCount(queryCondition);
		return Result.newInstance(count, "获取数据成功", true);
	}
}
