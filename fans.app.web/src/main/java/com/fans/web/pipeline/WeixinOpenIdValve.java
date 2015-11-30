package com.fans.web.pipeline;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.pipeline.PipelineContext;
import com.alibaba.citrus.service.pipeline.Valve;
import com.fans.web.webpage.RequestSessionBase;

public class WeixinOpenIdValve extends RequestSessionBase implements Valve{

	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private HttpServletResponse response;
	
	@Override
	public void invoke(PipelineContext pipelineContext) throws Exception {
		try {
			String openId = "123123123";
			super.setOpenId(response, openId);
		} finally {
			pipelineContext.invokeNext();
		}
	}
}
