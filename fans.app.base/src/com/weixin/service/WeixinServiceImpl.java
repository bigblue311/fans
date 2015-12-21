package com.weixin.service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.rmi.ConnectException;
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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.victor.framework.common.tools.JsonTools;
import com.victor.framework.common.tools.MD5;
import com.victor.framework.common.tools.StringTools;
import com.victor.framework.common.tools.UUIDTools;
import com.weixin.model.WxConfig;
import com.weixin.model.WxPay;
import com.weixin.model.WxUser;

public class WeixinServiceImpl implements WeixinService{

    private String appId="wx65bcca07aa8b4a53";
    private String appSecret;
    private String mchId="1290486501";
    private String reUrl;
    private String key = "wtHp80kLn7xG78uoEhkgCC5HaS2rAhOe";
    
    @Override
    public String getOauth2Url() {
        return WxConfig.getOauth2Url(appId, reUrl);
    }

    @Override
    public WxUser getUserInfo(String code) {
        String url = WxConfig.getAccessTokenUrl(appId, appSecret, code);
        String result = httpsRequest(url,null);
        JSONObject json = JSON.parseObject(result);
        
        String openId = json.getString("openid");
        String accessToken = json.getString("access_token");
        
        url = WxConfig.getUserInfoUrl(accessToken, openId);
        result = httpsRequest(url,null);
        WxUser wxUser = JsonTools.fromJson(result, WxUser.class);
        wxUser.setAccessToken(accessToken);
        wxUser.setOpenId(openId);
        return wxUser;
    }

    @Override
    public WxPay getUnifiedorder(String openId) {
        String result = httpsRequest(WxConfig.UNIFILED_ORDER, getPostData(openId));
        System.out.println(result);
        WxPay wxPay = new WxPay();
        try {
            Map<String,String> map = doXMLParse(result);
            for(String key : map.keySet()){
                if(StringTools.isEmpty(key)) continue;
                System.out.println(key+":"+map.get(key));
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
    
    private String getPostData(String openId){
        SortedMap<String,String> parameters = new TreeMap<String,String>();
        parameters.put("appid", appId);
        parameters.put("body", "test");
        parameters.put("mch_id", mchId);
        parameters.put("nonce_str", UUIDTools.getID());
        parameters.put("notify_url", "http://wxt.wetuan.com/wxcallBack.htm");
        parameters.put("openid", openId);
        parameters.put("out_trade_no", UUIDTools.getID());
        parameters.put("total_fee", "1");
        parameters.put("trade_type", "JSAPI");
        String sign = createSign(parameters);
        System.out.println(sign);
        parameters.put("sign", sign);
        String postData = getRequestXml(parameters);
        System.out.println(postData);
        return postData;
    }
    
    @Override
    public String createSign(SortedMap<String,String> parameters){
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
        System.out.println(sb.toString());
        String sign = MD5.getMD5(sb.toString()).toUpperCase();
        return sign;
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
    
    private String httpsRequest(String requestUrl, String outputStr) {
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
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
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
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
        } catch (ConnectException ce) {
            ce.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static Map<String,String> doXMLParse(String strxml) throws JDOMException, IOException {
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
    
    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public void setReUrl(String reUrl) {
        this.reUrl = reUrl;
    }
    
    public void setKey(String key) {
        this.key = key;
    }

    public static void main(String[] args){
        WeixinServiceImpl test = new WeixinServiceImpl();
        test.getUnifiedorder("ogOTHwaJi6KDLOjDu-59Nze0YW8M");
    }
}
