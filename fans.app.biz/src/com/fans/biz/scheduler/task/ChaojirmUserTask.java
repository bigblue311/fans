package com.fans.biz.scheduler.task;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.springframework.beans.factory.annotation.Autowired;

import com.fans.biz.manager.UserManager;
import com.fans.biz.model.ChaojirmResult;
import com.fans.biz.model.CrawlerVO;
import com.fans.dal.enumerate.ExtSourceEnum;
import com.fans.dal.model.UserDO;
import com.victor.framework.batch.thread.ScheduledTask;
import com.victor.framework.common.tools.JsonTools;

public class ChaojirmUserTask extends ScheduledTask{

    private static final String HTTP_CONTENT_CHARSET = "utf-8";
    private static final Integer MAX_CONN = 50;
    private static final Integer MAX_TIME_OUT = 10000;
    
    private static Integer page = 1;
    private static String url = "http://m.chaojirm.com/fans.php?a=getfans&page=";
    
	public ChaojirmUserTask() {
		super(5L,TimeUnit.MINUTES);
	}
	
	@Autowired
	private UserManager userManager;
	
	@Override
	public void doWork() {
	    PostMethod post = new PostMethod(url+page);
	    String result = executeMethod(post);
	    ChaojirmResult chaojirmResult = JsonTools.fromJson(result, ChaojirmResult.class);
	    if(chaojirmResult == null || !chaojirmResult.getSta().equals("ok")){
	        return;
	    }
	    for(CrawlerVO each : chaojirmResult.getFlist()){
	        try {
                UserDO user = new UserDO();
                user.setExtId(each.getId());
                user.setHeadImg(each.getHeadimg());
                user.setExtCity(each.getCity());
                user.setExtSource(ExtSourceEnum.超级人脉.getCode());
                user.setNickName(each.getUsername());
                user.setQrcode(each.getQrcode());
                user.setDescription(each.getRemark());
                userManager.create(user);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
	    }
	    page++;
	}
	
	private String executeMethod(HttpMethod method) {
        method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, HTTP_CONTENT_CHARSET);
        try {
            MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
            HttpClient httpClient = new HttpClient(connectionManager);
            httpClient.getHttpConnectionManager().getParams().setMaxTotalConnections(MAX_CONN);
            httpClient.getHttpConnectionManager().getParams().setSoTimeout(MAX_TIME_OUT);
            httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(MAX_TIME_OUT);
            httpClient.executeMethod(method);
            if (method.getStatusCode() == HttpStatus.SC_OK) {
                InputStream is = method.getResponseBodyAsStream();
                StringBuffer out = new StringBuffer(); 
                byte[] b = new byte[4096]; 
                for(int n; (n = is.read(b)) !=-1;){ 
                    out.append(new String(b,0,n)); 
                } 
                return out.toString(); 
            }
        } catch (HttpException e) {
            System.err.println("Fatal protocol violation: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Fatal transport error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            method.releaseConnection();
        }
        return "";
    }
}
