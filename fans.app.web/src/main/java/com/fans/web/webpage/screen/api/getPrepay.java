package com.fans.web.webpage.screen.api;

import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.fans.web.constant.RequestSession;
import com.fans.web.webpage.RequestSessionBase;
import com.weixin.model.WxPay;
import com.weixin.service.WeixinService;

/**
 *  "appId" ： "wx2421b1c4370ec43b",     //公众号名称，由商户传入     
    "timeStamp"：" 1395712654",         //时间戳，自1970年以来的秒数     
    "nonceStr" ： "e61463f8efa94090b1f366cccfbbb444", //随机串     
    "package" ： "prepay_id=u802345jgfjsdfgsdg888",     
    "signType" ： "MD5",         //微信签名方式：     
    "paySign" ： "70EA570631E4BB79628FBCA90534C63FF7FADD89" //微信签名 
    
 * @author victorhan
 *
 */
public class getPrepay extends RequestSessionBase{
    
    @Autowired
    private WeixinService weixinService;
    
    @Autowired
    private HttpServletRequest request;
    
    public WxPay execute(){
        WxPay wxPay = weixinService.getUnifiedorder(RequestSession.openId());
        wxPay.setWxVersion(super.getWxVersion(request));
        
        SortedMap<String,String> parameters = new TreeMap<String,String>();
        parameters.put("appId", wxPay.getAppId());
        parameters.put("timeStamp", wxPay.getTimestamp());
        parameters.put("nonceStr", wxPay.getNonceStr());
        parameters.put("package", wxPay.getPackageValue());
        parameters.put("signType", "MD5");
        String paySign = weixinService.createSign(parameters);
        wxPay.setPaySign(paySign);
        return wxPay;
    }
}
