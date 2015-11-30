package com.fans.web.pipeline;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.pipeline.PipelineContext;
import com.alibaba.citrus.service.pipeline.Valve;
import com.fans.biz.manager.UserManager;
import com.fans.dal.model.UserDO;
import com.fans.dal.query.UserQueryCondition;
import com.fans.web.constant.RequestSession;
import com.fans.web.webpage.RequestSessionBase;

public class RequestSessionValve extends RequestSessionBase implements Valve {

	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private HttpServletResponse response;
	
	@Autowired
	private UserManager userManager;
	
	@Override
	public void invoke(PipelineContext pipelineContext) throws Exception {
		try {
		    String openId = super.getOpenId(request);
		    RequestSession.openId(openId);
		    
		    UserDO userDO = userManager.getByOpenId(openId);
		    RequestSession.userDO(userDO);
		    
		    UserQueryCondition queryCondition = super.getQuery(request);
		    RequestSession.queryCondition(queryCondition);
		} finally {
			pipelineContext.invokeNext();
		}
	}
}
