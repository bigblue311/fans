package com.fans.web.pipeline;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.pipeline.PipelineContext;
import com.alibaba.citrus.service.pipeline.Valve;
import com.fans.biz.manager.WeixinManager;
import com.fans.web.constant.CookieKey;
import com.fans.web.webpage.RequestSessionBase;
import com.victor.framework.common.tools.StringTools;

public class WeixinOpenIdValve extends RequestSessionBase implements Valve{

	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private HttpServletResponse response;
	
	@Autowired
	private WeixinManager weixinManager;
	
	@Override
	public void invoke(PipelineContext pipelineContext) throws Exception {
		try {
		    String _setOpenId = request.getParameter("_setOpenId");
		    String domain = super.getDomain(request);
		    if(StringTools.isNotEmpty(_setOpenId)){
		        String uri = request.getRequestURI();
	            if(uri.endsWith(".htm") || uri.endsWith(".html")){
	                super.setCookie(response, CookieKey.RE_URL, uri);
	            }
		        response.sendRedirect(weixinManager.getOauth2Url(domain));
	            return;
		    }
		} finally {
			pipelineContext.invokeNext();
		}
	}
}
