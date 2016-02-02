package com.fans.web.webpage.screen;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;

import com.fans.biz.manager.UserManager;
import com.fans.biz.manager.WeixinManager;
import com.fans.dal.cache.SystemConfigCache;
import com.fans.dal.enumerate.SystemConfigKeyEnum;
import com.fans.dal.model.QrcodeDO;
import com.victor.framework.common.tools.StringTools;

public class WxNotifyCallback {

    @Autowired
    private HttpServletRequest request;
    
    @Autowired
    private HttpServletResponse response;
    
    @Autowired
    private WeixinManager weixinManager;
    
    @Autowired
    private UserManager userManager;
    
    @Autowired
    private SystemConfigCache systemConfigCache;
    
    public void execute() throws IOException, JDOMException{
    	try {
			InputStream inStream = request.getInputStream();
			ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = inStream.read(buffer)) != -1) {
				outSteam.write(buffer, 0, len);
			}
			outSteam.close();
			inStream.close();
			//获取微信调用我们notify_url的返回信息
			String result = new String(outSteam.toByteArray(), "UTF-8");
			Map<String, String> map = weixinManager.doXMLParse(result);
			
			String openId = map.get("FromUserName");
			String event = map.get("Event");
			String eventKey = map.get("EventKey");
			if(StringTools.isNotEmpty(event) && StringTools.isNotEmpty(eventKey)){
			    if(event.equals("subscribe") && eventKey.startsWith("qrscene_")){
	                Long qrcodeId = Long.parseLong(eventKey.replace("qrscene_",""));
	                weixinManager.doScan(qrcodeId, openId);
	                scanAddCoins(qrcodeId);
	            }
			}
			response.getWriter().write(setXML("SUCCESS", "")); 
		} catch (Exception e) {
			//告诉微信服务器，我收到信息了，不要在调用回调action了
			response.getWriter().write(setXML("SUCCESS", "")); 
		}
    }
    
    private void scanAddCoins(Long qrcodeId){
        QrcodeDO qrcodeDO = weixinManager.getQrcodeById(qrcodeId);
        if(qrcodeDO!=null){
            Long userId = qrcodeDO.getUserId();
            Integer coins = systemConfigCache.getCacheInteger(SystemConfigKeyEnum.SCAN_COINS.getCode(), 10);
            userManager.addCoins(userId, coins);
        }
    }
    
    private String setXML(String return_code, String return_msg) {
        return "<xml><return_code><![CDATA[" + return_code
                + "]]></return_code><return_msg><![CDATA[" + return_msg
                + "]]></return_msg></xml>";
    }
}
