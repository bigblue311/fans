package com.pg.web.admin.webpage.screen.api;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.google.common.collect.Lists;
import com.pg.biz.manager.TransactionManager;
import com.pg.biz.model.OrderVO;
import com.pg.dal.model.CustomerDO;
import com.pg.dal.query.OrderQueryCondition;
import com.pg.web.admin.common.AuthenticationToken;
import com.victor.framework.common.shared.Result;
import com.victor.framework.common.tools.StringTools;

public class GetMyOrderList {
	
	@Autowired
	private TransactionManager transactionManager;
	
	@Autowired
	private HttpSession session;
	
	public Result<List<OrderVO>> execute(@Param("status") String status){
		List<OrderVO> list = Lists.newArrayList();
		CustomerDO customer = AuthenticationToken.getLoginedCustomer(session);
		if(customer == null){
			return Result.newInstance(list, "登陆已失效", false);
		}
		
		OrderQueryCondition queryCondition = new OrderQueryCondition();
		if(StringTools.isNotEmpty(status)){
			queryCondition.setStatus(status);
		}
		queryCondition.setCustomerId(customer.getId());
		list = transactionManager.getOrderVOList(queryCondition);
		return Result.newInstance(list, "获取数据成功", true);
	}
}
