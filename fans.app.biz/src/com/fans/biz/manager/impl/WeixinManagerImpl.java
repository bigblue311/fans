package com.fans.biz.manager.impl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fans.biz.manager.WeixinManager;
import com.fans.dal.cache.WeixinConfigCache;
import com.fans.dal.dao.QrcodeDAO;
import com.fans.dal.dao.QrcodeScanDAO;
import com.fans.dal.model.QrcodeDO;
import com.fans.dal.model.QrcodeScanDO;
import com.fans.dal.model.UserDO;
import com.fans.dal.model.WeixinConfigDO;
import com.google.common.collect.Maps;
import com.victor.framework.common.tools.DateTools;
import com.victor.framework.common.tools.JsonTools;
import com.victor.framework.common.tools.MD5;
import com.victor.framework.common.tools.SHA1;
import com.victor.framework.common.tools.StringTools;
import com.victor.framework.common.tools.UUIDTools;
import com.victor.framework.dal.cache.GuavaCache;
import com.weixin.model.JsApiConfig;
import com.weixin.model.WxConfig;
import com.weixin.model.WxPayResponse;
import com.weixin.model.WxPayRequest;
import com.weixin.model.WxUser;

public class WeixinManagerImpl implements WeixinManager{

    private static GuavaCache cache = new GuavaCache(7000);  
    
    @Autowired
    private WeixinConfigCache weixinConfigCache;
    
    @Autowired
    private QrcodeDAO qrcodeDao;
    
    @Autowired
    private QrcodeScanDAO qrcodeScanDao;
    
    @Override
    public String getOauth2Url(String domain) {
        WeixinConfigDO configDO = weixinConfigCache.getCache(domain);
        String appId = configDO.getAppId();
        String reUrl = configDO.getReUrl();
        return WxConfig.getOauth2Url(appId, reUrl);
    }

    @Override
    public WxUser getUserInfo(String domain, String code) {
        WeixinConfigDO configDO = weixinConfigCache.getCache(domain);
        String appId = configDO.getAppId();
        String appSecret = configDO.getAppSecret();
        
        String url = WxConfig.getAccessTokenUrl(appId, appSecret, code);
        String result = httpRequest(url,null);
        JSONObject json = JSON.parseObject(result);
        
        String openId = json.getString("openid");
        String accessToken = json.getString("access_token");
        
        url = WxConfig.getUserInfoUrl(accessToken, openId);
        result = httpRequest(url,null);
        WxUser wxUser = JsonTools.fromJson(result, WxUser.class);
        wxUser.setAccessToken(accessToken);
        wxUser.setOpenId(openId);
        return wxUser;
    }

    @Override
    public WxPayResponse getUnifiedorder(String domain, WxPayRequest wxPayRequest) {
        String result = httpRequest(WxConfig.getUnifiledOrderUrl(), getPostData(domain, wxPayRequest));
        WxPayResponse wxPay = new WxPayResponse();
        try {
            Map<String,String> map = doXMLParse(result);
            for(String key : map.keySet()){
                if(StringTools.isEmpty(key)) continue;
                if(key.equals("result_code")){
                    wxPay.setResultCode(map.get(key));
                }
                if(key.equals("sign")){
                    wxPay.setSign(map.get(key));
                }
                if(key.equals("mch_id")){
                    wxPay.setMchId(map.get(key));
                }
                if(key.equals("prepay_id")){
                    wxPay.setPrepayId(map.get(key));
                }
                if(key.equals("return_msg")){
                    wxPay.setReturnMsg(map.get(key));
                }
                if(key.equals("appid")){
                    wxPay.setAppId(map.get(key));
                }
                if(key.equals("nonce_str")){
                    wxPay.setNonceStr(map.get(key));
                }
                if(key.equals("return_code")){
                    wxPay.setReturnCode(map.get(key));
                }
                if(key.equals("trade_type")){
                    wxPay.setTradeType(map.get(key));
                }
            }
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wxPay;
    }
    
    private String getPostData(String domain, WxPayRequest wxPayRequest){
        WeixinConfigDO configDO = weixinConfigCache.getCache(domain);
        String appId = configDO.getAppId();
        String mchId = configDO.getMchId();
        String key = configDO.getKey();
        
        SortedMap<String,String> parameters = new TreeMap<String,String>();
        parameters.put("appid", 		appId);
        parameters.put("body", 			wxPayRequest.getDescription());
        parameters.put("mch_id", 		mchId);
        parameters.put("nonce_str", 	UUIDTools.getID());
        parameters.put("notify_url",	wxPayRequest.getNotifyUrl());
        parameters.put("openid", 		wxPayRequest.getOpenId());
        parameters.put("out_trade_no", 	wxPayRequest.getTradeNo());
        parameters.put("total_fee", 	wxPayRequest.getTotalFee().toPlainString());
        parameters.put("trade_type", 	"JSAPI");
        //parameters.put("trade_type", 	"NATIVE");
        String sign = createSignMD5(key, parameters);
        parameters.put("sign", sign);
        String postData = getRequestXml(parameters);
        return postData;
    }
    
    @Override
    public String createSignMD5(String key, SortedMap<String,String> parameters){
        StringBuffer sb = new StringBuffer();
        
        List<String> keys = new ArrayList<String>(parameters.keySet());
        Collections.sort(keys);

        for (int i = 0; i < keys.size(); i++) {
        	String k = keys.get(i);
            String v = parameters.get(k);
            if(StringTools.isNotEmpty(v) && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + key);
        return MD5.getMD5(sb.toString()).toUpperCase();
    }
    
    private String getRequestXml(SortedMap<String,String> parameters){
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        Set<Map.Entry<String, String>> es = parameters.entrySet();
        Iterator<Map.Entry<String, String>> it = es.iterator();
        while(it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            String k = (String)entry.getKey();
            String v = (String)entry.getValue();
            if ("attach".equalsIgnoreCase(k)||"body".equalsIgnoreCase(k)||"sign".equalsIgnoreCase(k)) {
                sb.append("<"+k+">"+"<![CDATA["+v+"]]></"+k+">");
            }else {
                sb.append("<"+k+">"+v+"</"+k+">");
            }
        }
        sb.append("</xml>");
        return sb.toString();
    }
    
    private String httpRequest(String requestUrl, String outputStr) {
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 当outputStr不为null时向输出流写数据
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }
            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            return buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public Map<String,String> doXMLParse(String strxml) throws JDOMException, IOException {
        strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");
        if(null == strxml || "".equals(strxml)) {
            return null;
        }
        Map<String,String> m = Maps.newHashMap();
        InputStream in = new ByteArrayInputStream(strxml.getBytes("UTF-8"));
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(in);
        Element root = doc.getRootElement();
        @SuppressWarnings("unchecked")
        List<Element> list = root.getChildren();
        Iterator<Element> it = list.iterator();
        while(it.hasNext()) {
            Element e = (Element) it.next();
            String k = e.getName();
            String v = "";
            @SuppressWarnings("unchecked")
            List<Element> children = e.getChildren();
            if(children.isEmpty()) {
                v = e.getTextNormalize();
            } else {
                v = getChildrenText(children);
            }
            m.put(k, v);
        }
        //关闭流
        in.close();
        return m;
    }
    
    private String getJsApiAccessToken(String appId, String appSecret){
        String accessToken = cache.get(appId+":getJsApiAccessToken");
        if(StringTools.isEmpty(accessToken)){
            String result = httpRequest(WxConfig.getJSAPIAccessToken(appId, appSecret), null);
            JSONObject json = JSON.parseObject(result);
            
            accessToken = json.getString("access_token");
            cache.put(appId+":getJsApiAccessToken", accessToken);
        }
        return accessToken;
    }
    
    @Override
    public String getJsApiTicket(String domain) {
        WeixinConfigDO configDO = weixinConfigCache.getCache(domain);
        String appId = configDO.getAppId();
        String appSecret = configDO.getAppSecret();
        
        String ticket = cache.get(appId+":getJsApiTicket");
        if(StringTools.isEmpty(ticket)){
            String accessToken = getJsApiAccessToken(appId, appSecret);
            String result = httpRequest(WxConfig.getJSAPITicket(accessToken), null);
            JSONObject json = JSON.parseObject(result);
            String errmsg = json.getString("errmsg");
            if("ok".equals(errmsg)){
                ticket = json.getString("ticket");
                cache.put(appId+":getJsApiTicket", ticket);
            } else {
                System.out.println("failed to get jsapi ticket =" + errmsg);
                return null;
            }
        }
        return ticket;
    }
    
    @Override
    public JsApiConfig getJsApiConfig(String domain, String url) {
        WeixinConfigDO configDO = weixinConfigCache.getCache(domain);
        String appId = configDO.getAppId();
        
        String ticket = getJsApiTicket(domain);
        String nonceStr = UUIDTools.getID();
        String ts = Long.toString(DateTools.today().getTime()/1000);
        
        String str = "jsapi_ticket=" + ticket + "&noncestr=" + nonceStr + "&timestamp="+ ts +"&url=" + url;
        String sign = new SHA1().getDigestOfString(str.getBytes()); 
        
        JsApiConfig config = new JsApiConfig();
        config.setAppId(appId);
        config.setNonceStr(nonceStr);
        config.setTimestamp(ts);
        config.setSign(sign);
        return config;
    }
    
    /**
     * 获取子结点的xml
     * @param children
     * @return String
     */
    public static String getChildrenText(List<Element> children) {
        StringBuffer sb = new StringBuffer();
        if(!children.isEmpty()) {
            Iterator<Element> it = children.iterator();
            while(it.hasNext()) {
                Element e = it.next();
                String name = e.getName();
                String value = e.getTextNormalize();
                @SuppressWarnings("unchecked")
                List<Element> list = e.getChildren();
                sb.append("<" + name + ">");
                if(!list.isEmpty()) {
                    sb.append(getChildrenText(list));
                }
                sb.append(value);
                sb.append("</" + name + ">");
            }
        }
        
        return sb.toString();
    }
    
    @Override
    public QrcodeDO getUserQrcode(String domain, UserDO userDO) {
        if(userDO == null){
            return null;
        }
        
        WeixinConfigDO configDO = weixinConfigCache.getCache(domain);
        if(configDO == null){
            return null;
        }
        String appId = configDO.getAppId();
        String appSecret = configDO.getAppSecret();
        
        String accessToken = getJsApiAccessToken(appId, appSecret);
        String url = WxConfig.getJSAPIQrcode(accessToken);
        
        QrcodeDO qrcodeDO = new QrcodeDO();
        qrcodeDO.setUserId(userDO.getId());
        qrcodeDO.setDomain(domain);
        qrcodeDO.setSkvId(userDO.getSkvId());
        qrcodeDO.setOpenId(userDO.getOpenId());
        Long id = qrcodeDao.insert(qrcodeDO);
        qrcodeDO.setId(id);
        
        String postData = "{\"expire_seconds\": 604800, \"action_name\": \"QR_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": "+qrcodeDO.getId()+"}}}";
        String result = httpRequest(url, postData);
        JSONObject json = JSON.parseObject(result);
        String errmsg = json.getString("errmsg");
        if(StringTools.isNotEmpty(errmsg)){
            System.out.println("failed to get jsapi qrcode =" + errmsg);
            return null;
        } else {
            String ticket = json.getString("ticket");
            qrcodeDO.setTicket(ticket);
            qrcodeDao.update(qrcodeDO);
            return qrcodeDO;
        }
    }

    public static void main(String[] args){
        WeixinManagerImpl test = new WeixinManagerImpl();
        System.out.println(test.getJsApiConfig("wz.wetuan.com","http://wz.wetuan.com/my.htm").getSign());
    }

    @Override
    public QrcodeDO getQrcodeById(Long id) {
        return qrcodeDao.getById(id);
    }

    @Override
    public QrcodeScanDO getScanByOpenId(String openId) {
        return qrcodeScanDao.getByOpenId(openId);
    }

    @Override
    public QrcodeScanDO doScan(Long qrcodeId, String openId) {
        try {
            QrcodeScanDO qrcodeScanDO = new QrcodeScanDO();
            qrcodeScanDO.setQrcodeId(qrcodeId);
            qrcodeScanDO.setOpenId(openId);
            Long id = qrcodeScanDao.insert(qrcodeScanDO);
            qrcodeScanDO.setId(id);
            return qrcodeScanDO;
        } catch (Exception e) {
            return getScanByOpenId(openId);
        }
    }

    @Override
    public void updateSkvId(String openId, Long skvId) {
        QrcodeScanDO qrcodeScanDO = getScanByOpenId(openId);
        if(qrcodeScanDO != null && qrcodeScanDO.getSkvId() == null){
            qrcodeScanDO.setSkvId(skvId);
            qrcodeScanDao.update(qrcodeScanDO);
        }
    }
}
