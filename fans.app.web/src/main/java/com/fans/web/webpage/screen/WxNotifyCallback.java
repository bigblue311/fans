package com.fans.web.webpage.screen;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
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
        // 微信加密签名  
        String signature = request.getParameter("signature");  
        // 时间戳  
        String timestamp = request.getParameter("timestamp");  
        // 随机数  
        String nonce = request.getParameter("nonce");  
        // 随机字符串  
        String echostr = request.getParameter("echostr");
  
        PrintWriter out = response.getWriter();  
        
        if (checkSignature(signature, timestamp, nonce)) {  
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
            } catch (Exception e) {
                //告诉微信服务器，我收到信息了，不要在调用回调action了
                e.printStackTrace();
            }
            out.print(echostr);  
        }
        out.close();  
        out = null; 
    }
    
    private void scanAddCoins(Long qrcodeId){
        QrcodeDO qrcodeDO = weixinManager.getQrcodeById(qrcodeId);
        if(qrcodeDO!=null){
            Long userId = qrcodeDO.getUserId();
            Integer coins = systemConfigCache.getCacheInteger(SystemConfigKeyEnum.SCAN_COINS.getCode(), 10);
            userManager.addCoins(userId, coins);
        }
    }
    
//    private String setXML(String return_code, String return_msg) {
//        return "<xml><return_code><![CDATA[" + return_code
//                + "]]></return_code><return_msg><![CDATA[" + return_msg
//                + "]]></return_msg></xml>";
//    }
    
    // 与接口配置信息中的Token要一致  
    private static String token = "tangzhejiafen";  
  
    /** 
     * 验证签名 
     *  
     * @param signature 
     * @param timestamp 
     * @param nonce 
     * @return 
     */  
    public boolean checkSignature(String signature, String timestamp, String nonce) {  
        String[] arr = new String[] { token, timestamp, nonce };  
        // 将token、timestamp、nonce三个参数进行字典序排序  
        Arrays.sort(arr);  
        StringBuilder content = new StringBuilder();  
        for (int i = 0; i < arr.length; i++) {  
            content.append(arr[i]);  
        }  
        MessageDigest md = null;  
        String tmpStr = null;  
  
        try {  
            md = MessageDigest.getInstance("SHA-1");  
            // 将三个参数字符串拼接成一个字符串进行sha1加密  
            byte[] digest = md.digest(content.toString().getBytes());  
            tmpStr = byteToStr(digest);  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        }  
  
        content = null;  
        // 将sha1加密后的字符串可与signature对比，标识该请求来源于微信  
        return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;  
    }  
  
    /** 
     * 将字节数组转换为十六进制字符串 
     *  
     * @param byteArray 
     * @return 
     */  
    private static String byteToStr(byte[] byteArray) {  
        String strDigest = "";  
        for (int i = 0; i < byteArray.length; i++) {  
            strDigest += byteToHexStr(byteArray[i]);  
        }  
        return strDigest;  
    }  
  
    /** 
     * 将字节转换为十六进制字符串 
     *  
     * @param mByte 
     * @return 
     */  
    private static String byteToHexStr(byte mByte) {  
        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };  
        char[] tempArr = new char[2];  
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];  
        tempArr[1] = Digit[mByte & 0X0F];  
  
        String s = new String(tempArr);  
        return s;  
    }  
}
