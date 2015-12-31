package com.fans.web.webpage.screen.api;

import java.math.BigDecimal;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.fans.biz.manager.TransactionManager;
import com.fans.biz.manager.PriceManager;
import com.fans.dal.cache.SystemConfigCache;
import com.fans.dal.enumerate.SystemConfigKeyEnum;
import com.fans.dal.enumerate.TopupStatusEnum;
import com.fans.dal.enumerate.TopupTypeEnum;
import com.fans.dal.model.TopupDO;
import com.fans.dal.model.UserDO;
import com.fans.web.constant.RequestSession;
import com.fans.web.webpage.RequestSessionBase;
import com.victor.framework.common.shared.Result;
import com.victor.framework.common.tools.StringTools;
import com.weixin.model.WxPayRequest;
import com.weixin.model.WxPayResponse;
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
public class GetPrepay extends RequestSessionBase{
    
    @Autowired
    private WeixinService weixinService;
    
    @Autowired
    private TransactionManager transactionManager;
    
    @Autowired
    private PriceManager priceManager;
    
    @Autowired
    private HttpServletRequest request;
    
    @Autowired
    private SystemConfigCache systemConfigCache;
    
    public Result<WxPayResponse> execute(@Param(name="cash", defaultValue="0") Integer cash,
    							 		 @Param(name="type", defaultValue="0") Integer type,
    							 		 @Param("data1") Integer data1){
    	UserDO userDO = RequestSession.userDO();
    	if(userDO == null || userDO.getId() == null || StringTools.isEmpty(userDO.getOpenId())){
    		return Result.newInstance(null, "用户不存在", false);
    	}
    	
    	TopupTypeEnum topupType = TopupTypeEnum.getByCode(type);
    	if(topupType == null){
    		return Result.newInstance(null, "业务类型不存在", false);
    	}
    	
    	cash = getPrice(topupType,data1,cash);
    	if(cash < 0){
    		return Result.newInstance(null, "交易金额不能为负数", false);
    	}
    	
    	if(super.getWxVersion(request) < 5){
    		return Result.newInstance(null, "您的微信版本低于5.0无法使用微信支付", false);
    	}
    	
    	TopupDO topupDO = new TopupDO();
    	topupDO.setUserId(userDO.getId());
    	topupDO.setOpenId(userDO.getOpenId());
    	topupDO.setAmount(cash);
    	topupDO.setStatus(TopupStatusEnum.待支付.getCode());
    	topupDO.setType(type);
    	topupDO.setDescription(topupType.getDesc());
    	if(data1!=null){
    		topupDO.setData1(data1+"");
    	}
    	transactionManager.createTopup(topupDO);
    	
    	WxPayRequest wxPayRequest = new WxPayRequest();
    	wxPayRequest.setOpenId(topupDO.getOpenId());
    	wxPayRequest.setDescription(topupType.getDesc());
    	wxPayRequest.setNotifyUrl("http://wxt.wetuan.com/wxCallback.htm");
    	wxPayRequest.setTradeNo(topupDO.getUuid());
    	if(systemConfigCache.getSwitch(SystemConfigKeyEnum.DEBUG_MODE.getCode())){
    		//测试模式下, 只交易1分钱
    		wxPayRequest.setTotalFee(new BigDecimal(1));
    	} else {
    		//转化成分
    		BigDecimal totalFee = new BigDecimal(topupDO.getAmount() * 100);
    		wxPayRequest.setTotalFee(totalFee);
    	}
    	
        WxPayResponse wxPay = weixinService.getUnifiedorder(wxPayRequest);
        
        topupDO.setWeixinPrepayResult(wxPay.getResultCode());
        transactionManager.updateTopup(topupDO);
        
        SortedMap<String,String> parameters = new TreeMap<String,String>();
        parameters.put("appId", wxPay.getAppId());
        parameters.put("timeStamp", wxPay.getTimestamp());
        parameters.put("nonceStr", wxPay.getNonceStr());
        parameters.put("package", wxPay.getPackageValue());
        parameters.put("signType", "MD5");
        String paySign = weixinService.createSignMD5(parameters);
        wxPay.setPaySign(paySign);
        return Result.newInstance(wxPay, "交易成功", true);
    }
    
    private Integer getPrice(TopupTypeEnum topupType, Integer data1, Integer cash){
    	if(topupType.getCode() == TopupTypeEnum.充值.getCode()){
    		return cash;
    	}
    	if(topupType.getCode() == TopupTypeEnum.购买VIP.getCode()){
    		return priceManager.buyVipUseMoney(data1);
    	}
    	if(topupType.getCode() == TopupTypeEnum.购买置顶.getCode()){
    		return priceManager.buyZhuangBUseMoney(data1);
    	}
    	return 0;
    }
}
