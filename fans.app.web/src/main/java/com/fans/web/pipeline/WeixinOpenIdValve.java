package com.fans.web.pipeline;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.pipeline.PipelineContext;
import com.alibaba.citrus.service.pipeline.Valve;

public class WeixinOpenIdValve implements Valve{

	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private HttpServletResponse response;
	
	@Override
	public void invoke(PipelineContext pipelineContext) throws Exception {
		try {
			System.out.println("this is my first application valve");
		} finally {
			pipelineContext.invokeNext();
		}
	}
}
