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
	
	//private static final String APP_ID = "wx65bcca07aa8b4a53";
	//private static final String APP_SECRET = "8ab8cbbdada8cb78f4f4a0d44e23709d";
	
	@Override
	public void invoke(PipelineContext pipelineContext) throws Exception {
		try {
		    String _setOpenId = request.getParameter("_setOpenId");
		    
		    if(StringTools.isNotEmpty(_setOpenId)){
		        response.sendRedirect(weixinService.getOauth2Url());
//		        String redirectUrl = "http://wxt.wetuan.com/api/setOpenId.json";
//	            response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + APP_ID
//	                                + "&response_type=code&scope=snsapi_userinfo&state=1&redirect_uri="
//	                                + redirectUrl + "#wechat_redirect");
	            return;
		    }
		} finally {
			pipelineContext.invokeNext();
		}
	}
}
