package com.fans.web.pipeline;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.pipeline.PipelineContext;
import com.alibaba.citrus.service.pipeline.Valve;
import com.fans.web.webpage.RequestSessionBase;
import com.victor.framework.common.tools.StringTools;
import com.weixin.service.WeixinService;

public class WeixinOpenIdValve extends RequestSessionBase implements Valve{

	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private HttpServletResponse response;
	
	@Autowired
	private WeixinService weixinService;
	
	@Override
	public void invoke(PipelineContext pipelineContext) throws Exception {
		try {
		    String _setOpenId = request.getParameter("_setOpenId");
		    
		    if(StringTools.isNotEmpty(_setOpenId)){
		        response.sendRedirect(weixinService.getOauth2Url());
	            return;
		    }
		} finally {
			pipelineContext.invokeNext();
		}
	}
}
