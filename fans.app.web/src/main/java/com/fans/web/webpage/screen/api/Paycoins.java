package com.fans.web.webpage.screen.api;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.fans.biz.manager.TransactionManager;
import com.fans.dal.enumerate.PayStatusEnum;
import com.fans.dal.model.UserDO;
import com.fans.web.constant.RequestSession;
import com.victor.framework.common.shared.Result;
import com.victor.framework.common.tools.StringTools;

public class Paycoins {
	
	@Autowired
    private TransactionManager transactionManager;
	
	public Result<Integer> execute(@Param(name="type", defaultValue="1") Integer type,
								   @Param(name="data1", defaultValue="0")Integer data1){
		
		System.out.println(type+":"+data1);
		UserDO userDO = RequestSession.userDO();
    	if(userDO == null || userDO.getId() == null || StringTools.isEmpty(userDO.getOpenId())){
    		return Result.newInstance(null, "用户不存在", false);
    	}
    	PayStatusEnum payStatus = transactionManager.buy(userDO.getId(), data1, type);
    	return Result.newInstance(payStatus.getCode(), payStatus.getDesc(), payStatus.getSuccess());
	}
}
