package com.fans.web.webpage.screen.api;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.fans.biz.manager.UserManager;
import com.fans.dal.model.UserDO;
import com.fans.web.webpage.RequestSessionBase;
import com.weixin.model.WxUser;
import com.weixin.service.WeixinService;

public class SetOpenId extends RequestSessionBase{
    
    @Autowired
    private HttpServletResponse response;
    
    @Autowired
    private WeixinService weixinService;
    
    @Autowired
    private UserManager userManager;
    
    public void execute(@Param("code")String code, Navigator nav){
		WxUser wxUser = weixinService.getUserInfo(code);
		String openId = wxUser.getOpenId();
        UserDO userDO = userManager.getByOpenId(openId);
        if(userDO == null){
            userDO = new UserDO();
            userDO.setOpenId(openId);
            userDO.setNickName(wxUser.getNickName());
            userDO.setHeadImg(wxUser.getHeadImgUrl());
            userDO.setGender(getSex(wxUser.getSex()));
            userDO.setCoins(0);
            userManager.create(userDO);
        }
        super.setOpenId(response, openId);
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
}
