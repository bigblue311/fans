package com.fans.web.webpage.screen;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.fans.biz.manager.UserManager;
import com.fans.dal.enumerate.SearchTypeEnum;
import com.fans.dal.model.UserDO;
import com.fans.dal.query.UserQueryCondition;
import com.fans.web.constant.RequestSession;
import com.fans.web.webpage.RequestSessionBase;
import com.victor.framework.dal.basic.Paging;

public class Index extends RequestSessionBase{
    @Autowired
    private UserManager userManager;
    
    @Autowired
    private HttpServletResponse response;
    
    public void execute(@Param("searchType") Integer searchType, Context context){
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
            default:
                break;
            }
        }
        
        userQueryCondition.valid().setPageSize(UserQueryCondition.DEFAULT_PAGE_SIZE).setPage(1);
        Paging<UserDO> paging = userManager.getPage(userQueryCondition);
        UserDO userDO = RequestSession.userDO();
        
        loadPriceSet(context);
        context.put("searchType", userQueryCondition.getSearchType());
        context.put("query", userQueryCondition);
        context.put("topList", userManager.getTopUsers(RequestSession.openId()));
        context.put("list", paging.getData());
        context.put("user", userDO);
        context.put("nextRefresh", userManager.nextRefresh(userDO));
    }
}
