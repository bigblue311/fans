package com.fans.web.pipeline;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.pipeline.PipelineContext;
import com.alibaba.citrus.service.pipeline.Valve;
import com.fans.biz.manager.UserManager;
import com.fans.dal.cache.SystemConfigCache;
import com.fans.dal.enumerate.SystemConfigKeyEnum;
import com.fans.dal.model.UserDO;
import com.fans.dal.query.UserQueryCondition;
import com.fans.web.constant.RequestSession;
import com.fans.web.webpage.RequestSessionBase;
import com.victor.framework.common.tools.StringTools;

public class RequestSessionValve extends RequestSessionBase implements Valve {

	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private HttpServletResponse response;
	
	@Autowired
	private UserManager userManager;
	
	@Autowired
    private SystemConfigCache systemConfigCache;
	
	@Override
	public void invoke(PipelineContext pipelineContext) throws Exception {
		try {
		    
		    String openId = super.getOpenId(request);
		    if(systemConfigCache.getSwitch(SystemConfigKeyEnum.SYSTEM_DEBUG_MODE.getCode())){
		        if(StringTools.isEmpty(openId)){
		            openId = "ogOTHwaJi6KDLOjDu-59Nze0YW8M";
		            super.setOpenId(response, openId);
		        }
            }
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
