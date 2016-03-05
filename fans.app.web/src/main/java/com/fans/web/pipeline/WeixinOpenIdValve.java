package com.fans.web.pipeline;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.pipeline.PipelineContext;
import com.alibaba.citrus.service.pipeline.Valve;
import com.fans.biz.manager.WeixinManager;
import com.fans.dal.cache.SystemConfigCache;
import com.fans.dal.enumerate.SystemConfigKeyEnum;
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
	
	@Autowired
    private SystemConfigCache systemConfigCache;
	
	@Override
	public void invoke(PipelineContext pipelineContext) throws Exception {
		try {
		    String _setOpenId = request.getParameter("_setOpenId");
		    String domain = super.getDomain(request);
		    if(StringTools.isNotEmpty(_setOpenId)){
		        
		        String _goWetuan = request.getParameter("_goWetuan");
		        String uri = request.getRequestURI();
		        if(StringTools.isNotEmpty(_goWetuan)){
		            String wetuanHost = systemConfigCache.getCacheString(SystemConfigKeyEnum.WETUAN_HOST.getCode(), "wetuan.com");
		            if(_goWetuan.equals("1")){
		                uri = "http://"+wetuanHost+"/index.htm?openId=[openId]";
		            }
		            if(_goWetuan.equals("2")){
                        uri = "http://"+wetuanHost+"/user/login.htm?upId=[upId]&openId=[openId]";
                    }
		            if(_goWetuan.equals("3")){
		                uri = "http://"+wetuanHost+"/user/toRegist.htm?upId=[upId]&openId=[openId]";
		            }
		        }
	            if(uri.contains(".htm") || uri.contains(".html")){
	                String contextRoot = request.getContextPath();
	                if(StringTools.isNotEmpty(contextRoot)){
	                    uri = uri.replace(contextRoot, "");
	                }
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
