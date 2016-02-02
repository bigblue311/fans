package com.fans.web.pipeline;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;

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
import com.victor.framework.common.tools.DateTools;
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
            String domain = super.getDomain(request);
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
            if(skvId!=null){
                super.setCookie(response, CookieKey.SKV_ID, skvId+"");
            }
            

            UserDO userDOFromOpenId = userManager.getByOpenId(openId);
            UserDO userDOFromSkvId = userManager.getBySkvId(skvId);
            
            SkvUserDO skvUser = userManager.getSkvUserById(skvId);
            userDOFromSkvId = userMerge(userDOFromSkvId, skvUser);
            
            UserDO merged = userMerge(userDOFromOpenId, userDOFromSkvId);
            if(merged != null && merged.getId() == null){
                merged.setCoins(systemConfigCache.getCacheInteger(SystemConfigKeyEnum.NEW_COINS.getCode(), 0));
                Integer dayToAdd = systemConfigCache.getCacheInteger(SystemConfigKeyEnum.NEW_VIP.getCode(), 0);
                Date vipExpire = DateTools.addDate(DateTools.today(), dayToAdd);
                merged.setGmtVipExpire(vipExpire);
                merged.setDomain(domain);
                userManager.create(merged);
            }
            
            updateQrcodeScan(merged);
            //setUser
            RequestSession.userDO(merged);
            
            UserQueryCondition queryCondition = super.getQuery(request);
            RequestSession.queryCondition(queryCondition);
        } finally {
            pipelineContext.invokeNext();
        }
    }
    
    private UserDO userMerge(UserDO userDO, SkvUserDO skvUser){
        if(userDO != null && skvUser == null){
            if(userDO.getSkvId() == null){
                RequestSession.level(null);
                return userDO;
            }
            skvUser = userManager.getSkvUserById(userDO.getSkvId());
            if(skvUser == null){
                RequestSession.level(null);
                return userDO;
            }
        }
        if(userDO == null && skvUser != null){
            String shoppingLevel = skvUser.getShoppingLevel();
            ShoppingLevelEnum level = ShoppingLevelEnum.getByCode(shoppingLevel);
            RequestSession.level(level);
            
            userDO = new UserDO();
            userDO.setSkvId(skvUser.getId());
            userDO.setPhone(skvUser.getPhone()); 
            //userDO.setHeadImg("http://wetuan.com/xls/"+skvUser.getUserImage());
            userDO.setNickName(skvUser.getUserName());
            return userDO;
        }
        if(userDO == null && skvUser == null){
            RequestSession.level(null);
            return null;
        }
        String shoppingLevel = skvUser.getShoppingLevel();
        ShoppingLevelEnum level = ShoppingLevelEnum.getByCode(shoppingLevel);
        RequestSession.level(level);
        
        userDO.setSkvId(skvUser.getId());
        userDO.setPhone(skvUser.getPhone()); 
//        if(StringUtil.isEmpty(userDO.getHeadImg())){
//            userDO.setHeadImg("http://wetuan.com/xls/"+skvUser.getUserImage());
//        }
        userDO.setNickName(skvUser.getUserName());
        return userDO;
    }
    
    private UserDO userMerge(UserDO user1, UserDO user2){
        if(user1!=null && user2==null){
            return user1;
        }
        if(user1 == null && user2 != null){
            return user2;
        }
        if(user1 == null && user2 == null){
            return null;
        }
        UserDO merged = user1;
        Field[] fields = user1.getClass().getDeclaredFields();
        for(Field field : fields){
            String getMethod = "get"+field.getName();
            String setMethod = "set"+field.getName();
            Object obj1 = doGetMethod(user1,getMethod);
            Object obj2 = doGetMethod(user2,getMethod);
            if(obj1 == null && obj2 != null){
                doSetMethod(merged,setMethod,obj2);
            }
        }
        return merged;
    }
    
    private void updateQrcodeScan(UserDO userDO){
        if(userDO != null && userDO.getSkvId() != null 
                && StringTools.isNotEmpty(userDO.getOpenId())){
            weixinManager.updateSkvId(userDO.getOpenId(), userDO.getSkvId());
        }
    }
    
    private Object doGetMethod(UserDO userDO, String methodName){
        Method[] methods = userDO.getClass().getDeclaredMethods();
        for(Method method : methods){
            if(!method.getName().equalsIgnoreCase(methodName)){
                continue;
            }
            try {
                return method.invoke(userDO, new Object[0]);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }
    
    private void doSetMethod(UserDO userDO, String methodName, Object value){
        Method[] methods = userDO.getClass().getDeclaredMethods();
        for(Method method : methods){
            if(!method.getName().equalsIgnoreCase(methodName)){
                continue;
            }
            try {
                method.invoke(userDO, value);
            } catch (Exception e) {
                return;
            }
        }
    }
}
