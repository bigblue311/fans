package com.fans.web.webpage.screen.api;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.fans.biz.manager.UserManager;
import com.fans.biz.manager.WeixinManager;
import com.fans.dal.cache.SystemConfigCache;
import com.fans.dal.enumerate.SystemConfigKeyEnum;
import com.fans.dal.model.QrcodeScanDO;
import com.fans.dal.model.UserDO;
import com.fans.web.constant.CookieKey;
import com.fans.web.webpage.RequestSessionBase;
import com.victor.framework.common.tools.DateTools;
import com.victor.framework.common.tools.StringTools;
import com.weixin.model.WxUser;

public class SetOpenId extends RequestSessionBase{
    
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
    
    public void execute(@Param("code")String code, Navigator nav) throws IOException{
        String domain = super.getDomain(request);
        String openId = "";
        if(StringTools.isNotEmpty(code)){
            WxUser wxUser = weixinManager.getUserInfo(domain,code);
            UserDO userDO = userManager.getByOpenId(openId);
            if(wxUser!= null && userDO == null){
                openId = wxUser.getOpenId();
                userDO = new UserDO();
                userDO.setOpenId(openId);
                userDO.setNickName(wxUser.getNickName());
                userDO.setHeadImg(wxUser.getHeadImgUrl());
                userDO.setGender(getSex(wxUser.getSex()));
                userDO.setCoins(systemConfigCache.getCacheInteger(SystemConfigKeyEnum.NEW_COINS.getCode(), 0));
                Integer dayToAdd = systemConfigCache.getCacheInteger(SystemConfigKeyEnum.NEW_VIP.getCode(), 0);
                Date vipExpire = DateTools.addDate(DateTools.today(), dayToAdd);
                userDO.setGmtVipExpire(vipExpire);
                userDO.setDomain(domain);
                userManager.create(userDO);
            }
            if(openId == null){
                openId = "";
            }
            super.setOpenId(response, openId);
        }
        String reUrl = getReUrl(request,openId);
        if(reUrl.startsWith("http")){
            response.sendRedirect(reUrl);
        } else {
            nav.redirectTo("app").withTarget(reUrl);
        }
    }
    
    private Integer getSex(Integer wxSex){
        if(wxSex == null){
            return null;
        }
        if(wxSex == 1){
            return 0;
        }
        if(wxSex == 2){
            return 1;
        }
        return null;
    }
    
    private String getUpId(String openId){
        if(StringTools.isEmpty(openId)){
            return "";
        }
        QrcodeScanDO qrcodeScanDO = weixinManager.getScanByOpenId(openId);
        if(qrcodeScanDO!=null){
            Long upperSkvId = qrcodeScanDO.getUpperSkvId();
            if(upperSkvId!=null){
                return upperSkvId.toString();
            }
        }
        return "";
    }
    
    private String getReUrl(HttpServletRequest request, String openId){
        String upId = getUpId(openId);
        String reUrl = super.getCookie(request, CookieKey.RE_URL);
        if(StringTools.isNotEmpty(reUrl)){
            if(reUrl.contains("[upId]")){
                reUrl = reUrl.replace("[upId]", upId);
            }
            if(reUrl.contains("[openId]")){
                reUrl = reUrl.replace("[openId]", openId);
            }
            return reUrl;
        } else {
            return "index.vm";
        }
    }
}
