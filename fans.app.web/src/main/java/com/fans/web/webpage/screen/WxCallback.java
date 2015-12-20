package com.fans.web.webpage.screen;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

public class WxCallback {

    @Autowired
    private HttpServletRequest request;
    
    public void execute(){
        String requestUrl = request.getRequestURL().toString();
        requestUrl+=(request.getQueryString()==null?"":"?"+request.getQueryString());
        System.out.println(requestUrl);
    }
}
