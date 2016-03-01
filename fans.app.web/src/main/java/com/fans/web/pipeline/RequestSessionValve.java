package com.fans.web.pipeline;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.pipeline.PipelineContext;
import com.alibaba.citrus.service.pipeline.Valve;
import com.fans.biz.manager.UserManager;
import com.fans.biz.manager.WeixinManager;
import com.fans.biz.threadLocal.RequestSession;
import com.fans.dal.cache.SystemConfigCache;
import com.fans.dal.enumerate.ShoppingLevelEnum;
import com.fans.dal.enumerate.SystemConfigKeyEnum;
import com.fans.dal.model.SkvUserDO;
import com.fans.dal.model.UserDO;
import com.fans.dal.query.UserQueryCondition;
import com.fans.web.constant.CookieKey;
import com.fans.web.webpage.RequestSessionBase;
import com.victor.framework.common.tools.StringTools;

public class RequestSessionValve extends RequestSessionBase implements Valve {

    @Autowired
    private HttpServletRequest request;
    
    @Autowired
    private HttpServletResponse response;
    
    @Autowired
    private UserManager userManager;
    
    @Autowired
    private SystemConfigCache systemConfigCache;
    
    @Autowired
    private WeixinManager weixinManager;
    
    @Override
    public void invoke(PipelineContext pipelineContext) throws Exception {
        try {
            String openId = super.getOpenId(request);
            Long skvId = super.getSkvId(request);
            
            if(systemConfigCache.getSwitch(SystemConfigKeyEnum.DEBUG_MODE.getCode())){
                if(StringTools.isEmpty(openId)){
                    openId = "ogOTHwaJi6KDLOjDu-59Nze0YW8M";
                    super.setOpenId(response, openId);
                }
                if(skvId == null){
                    skvId = 1l;
                }
            }
            RequestSession.openId(openId);

            UserDO userDO = userManager.getByOpenId(openId);
            if(userDO != null){
                if(userDO.getSkvId()!=null){
                    skvId = userDO.getSkvId();
                } else {
                    userDO = userManager.merge(userDO.getId(), skvId);
                }
            }
            
            if(skvId!=null){
                SkvUserDO skvUser = userManager.getSkvUserById(skvId);
                if(skvUser!=null){
                    String shoppingLevel = skvUser.getShoppingLevel();
                    ShoppingLevelEnum level = ShoppingLevelEnum.getByCode(shoppingLevel);
                    RequestSession.level(level);
                }
                super.setCookie(response, CookieKey.SKV_ID, skvId+"");
            }
            
            updateQrcodeScan(userDO);
            //setUser
            RequestSession.userDO(userDO);
            
            UserQueryCondition queryCondition = super.getQuery(request);
            RequestSession.queryCondition(queryCondition);
        } finally {
            pipelineContext.invokeNext();
        }
    }
    
    private void updateQrcodeScan(UserDO userDO){
        if(userDO != null && userDO.getSkvId() != null 
                && StringTools.isNotEmpty(userDO.getOpenId())){
            weixinManager.updateSkvId(userDO.getOpenId(), userDO.getSkvId());
        }
    }
}
