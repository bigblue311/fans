package com.fans.web.webpage.screen;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.fans.biz.manager.UserManager;
import com.fans.biz.threadLocal.RequestSession;
import com.fans.dal.cache.LocationCache;
import com.fans.dal.enumerate.SearchTypeEnum;
import com.fans.dal.model.LocationDO;
import com.fans.dal.model.UserDO;
import com.fans.dal.query.UserQueryCondition;
import com.fans.web.webpage.RequestSessionBase;
import com.victor.framework.common.tools.StringTools;
import com.victor.framework.dal.basic.Paging;

public class Index extends RequestSessionBase{
    @Autowired
    private UserManager userManager;
    
    @Autowired
    private HttpServletResponse response;
    
    @Autowired
    private LocationCache locationCache;
    
    public void execute(@Param("searchType") Integer searchType, Context context){
        UserQueryCondition userQueryCondition = getQueryCondition(searchType);
        Paging<UserDO> paging = userManager.getPage(userQueryCondition);
        UserDO userDO = RequestSession.userDO();
        
        loadPriceSet(context);
        context.put("searchType", userQueryCondition.getSearchType());
        context.put("query", userQueryCondition);
        if(userDO != null){
            context.put("topList", userManager.getTopUsers());
        }
        context.put("list", paging.getData());
        context.put("user", userDO);
        context.put("nextRefresh", userManager.nextRefresh(userDO));
        context.put("todayTask", userManager.getTodayTask(userDO));
    }
    
    private UserQueryCondition getQueryCondition(Integer searchType){
        UserQueryCondition userQueryCondition = RequestSession.queryCondition();
        if(searchType != null){
            switch(searchType){
            case 0:
                userQueryCondition.searchPerson();
                super.setSearchType(response, SearchTypeEnum.个人二维码.getCode());
                break;
            case 1:
                userQueryCondition.searchGroup();
                super.setSearchType(response, SearchTypeEnum.群二维码.getCode());
                break;
            case 2:
                userQueryCondition.searchFans();
                super.setSearchType(response, SearchTypeEnum.关注我的.getCode());
                UserDO userDO = RequestSession.userDO();
                if(userDO != null && userDO.getId()!=null){
                    userQueryCondition.setShareUserId(userDO.getId());
                }
                break;
            default:
                break;
            }
        }
        
        String province = userQueryCondition.getProvince();
        String city = userQueryCondition.getCity();
        
        if(StringTools.isNotEmpty(province) && StringTools.isNotEmpty(city)){
            LocationDO location = locationCache.getCache(province+","+city);
            if(location!=null){
                String extCity = location.getName().replace("省", "").replace("市", "");
                userQueryCondition.setExtCity(extCity);
            }
        }
        userQueryCondition.valid().setPageSize(UserQueryCondition.DEFAULT_PAGE_SIZE).setPage(1);
        return userQueryCondition;
    }
}
