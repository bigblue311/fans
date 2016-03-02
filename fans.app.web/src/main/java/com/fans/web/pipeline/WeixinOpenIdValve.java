package com.fans.web.pipeline;

import static com.alibaba.citrus.turbine.util.TurbineUtil.getTurbineRunData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.pipeline.PipelineContext;
import com.alibaba.citrus.service.pipeline.Valve;
import com.alibaba.citrus.turbine.TurbineRunDataInternal;
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
		    boolean isWeixin = super.isWeixinUser(request);
		    if(!isWeixin){
		        String path = request.getRequestURI();
		        if(path.equals("/weixin.htm")){
		            return;
		        }
		        TurbineRunDataInternal rundata = (TurbineRunDataInternal) getTurbineRunData(request);
		        rundata.redirectTo("app").withTarget("weixin.htm");
		        return;
		    }
		    if(StringTools.isNotEmpty(_setOpenId)){
		        
		        String _goWetuan = request.getParameter("_goWetuan");
		        String uri = request.getRequestURI();
		        if(StringTools.isNotEmpty(_goWetuan)){
		            if(_goWetuan.equals("1")){
		                uri = "http://wetuan.com/index.htm?openId=[openId]";
		            }
		            if(_goWetuan.equals("2")){
                        uri = "http://wetuan.com/user/login.htm?upId=[upId]&openId=[openId]";
                    }
		            if(_goWetuan.equals("3")){
		                uri = "http://wetuan.com/user/toRegist.htm?upId=[upId]&openId=[openId]";
		            }
		        }
	            if(uri.contains(".htm") || uri.contains(".html")){
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
