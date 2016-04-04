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
import com.fans.dal.model.QrcodeDO;
import com.fans.dal.model.QrcodeScanDO;
import com.fans.dal.model.SkvUserDO;
import com.fans.dal.model.UserDO;
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
            openId = wxUser.getOpenId();
            
            UserDO userDO = userManager.getByOpenId(openId);
            Long skvId = super.getSkvId(request);
            SkvUserDO skvUserDO = userManager.getSkvUserByOpenId(openId);
            if(skvUserDO!=null){
                skvId = skvUserDO.getId();
            }
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
                userDO.setSkvId(skvId);
                userManager.create(userDO);
            }
            super.setOpenId(response, openId);
        }
        nav.redirectTo("app").withTarget("index.vm");
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
    
    @SuppressWarnings("unused")
    private void sendAddFriendMsg(String nickName, String openId){
        if(StringTools.isEmpty(openId)){
            return;
        }
        QrcodeScanDO qrcodeScanDO = weixinManager.getSkvScanByOpenId(openId);
        if(qrcodeScanDO!=null){
            QrcodeDO qrcodeDO = weixinManager.getQrcodeById(qrcodeScanDO.getQrcodeId());
            if(qrcodeDO!=null && qrcodeDO.getOpenId() != null){
                weixinManager.sendText(super.getDomain(request), qrcodeDO.getOpenId(), "["+nickName+"]通过您分享的二维码,加入了躺着加粉");
            }
            if(qrcodeDO != null && qrcodeDO.getUserId() != null){
                Long userId = qrcodeDO.getUserId();
                UserDO user = userManager.getById(userId);
                if(user!=null){
                    weixinManager.sendText(super.getDomain(request), openId, "通过扫描["+user.getNickName()+"]的二维码,加入了躺着加粉");
                }
            }
        }
        return;
    }
}
