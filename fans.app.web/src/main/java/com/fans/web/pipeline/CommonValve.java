package com.fans.web.pipeline;

import static com.alibaba.citrus.turbine.util.TurbineUtil.getTurbineRunData;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.pipeline.PipelineContext;
import com.alibaba.citrus.service.pipeline.Valve;
import com.alibaba.citrus.turbine.TurbineRunDataInternal;

public class CommonValve implements Valve {

    @Autowired
    private HttpServletRequest request;
    
    @Override
    public void invoke(PipelineContext pipelineContext) throws Exception {
        try {
            TurbineRunDataInternal rundata = (TurbineRunDataInternal) getTurbineRunData(request);
            rundata.getContext().put("contextRoot", request.getContextPath());
        } finally {
            pipelineContext.invokeNext();
        }
    }
}
