package com.fans.web.webpage.screen.api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.fans.biz.manager.TransactionManager;
import com.fans.dal.enumerate.PayStatusEnum;
import com.fans.dal.enumerate.ShoppingLevelEnum;
import com.fans.dal.model.UserDO;
import com.fans.web.webpage.RequestSessionBase;
import com.victor.framework.common.shared.Result;

public class Paycoins extends RequestSessionBase{
	
	@Autowired
    private TransactionManager transactionManager;
	
	@Autowired
    private HttpServletRequest request;
	
	public Result<Integer> execute(@Param(name="type", defaultValue="1") Integer type,
								   @Param(name="data1", defaultValue="0")Integer data1){
        ShoppingLevelEnum level = super.getLevel(request);
		UserDO userDO = super.getUserDO(request);
    	if(userDO == null || userDO.getId() == null){
    		return Result.newInstance(null, "用户不存在", false);
    	}
    	PayStatusEnum payStatus = transactionManager.buy(userDO.getId(), data1, type,level);
    	return Result.newInstance(payStatus.getCode(), payStatus.getDesc(), payStatus.getSuccess());
	}
}
