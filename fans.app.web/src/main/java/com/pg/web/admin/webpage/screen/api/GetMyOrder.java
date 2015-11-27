package com.pg.web.admin.webpage.screen.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.pg.biz.manager.TransactionManager;
import com.pg.biz.model.OrderVO;
import com.pg.dal.model.CustomerDO;
import com.pg.web.admin.common.AuthenticationToken;
import com.victor.framework.common.shared.Result;

public class GetMyOrder {

	@Autowired
	private TransactionManager transactionManager;
	
	@Autowired
	private HttpSession session;
	
	public Result<OrderVO> execute(@Param("id") Long id){
		CustomerDO customer = AuthenticationToken.getLoginedCustomer(session);
		if(customer == null){
			return Result.newInstance(null, "登陆已失效", false);
		}
		
		OrderVO orderVO = transactionManager.getOrderVOById(id);
		return Result.newInstance(orderVO, "获取数据成功", true);
	}
	
}
