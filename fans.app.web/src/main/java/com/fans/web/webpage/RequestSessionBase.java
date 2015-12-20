package com.fans.web.webpage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fans.dal.enumerate.SearchTypeEnum;
import com.fans.dal.model.UserDO;
import com.fans.dal.query.UserQueryCondition;
import com.fans.web.constant.CookieKey;
import com.victor.framework.common.tools.JsonTools;
import com.victor.framework.common.tools.StringTools;

public abstract class RequestSessionBase extends CookieBase{
    
    public String getOpenId(HttpServletRequest request) {
        return super.getCookie(request, CookieKey.OPEN_ID);
    }
    
    public void setOpenId(HttpServletResponse response, String openId) {
        super.setCookie(response, CookieKey.OPEN_ID, openId);
    }
    
    public Integer getWxVersion(HttpServletRequest request) {
        String userAgent = request.getHeader("user-agent");
        char agent = userAgent.charAt(userAgent.indexOf("MicroMessenger")+15);
        String agentStr = new String(new char[]{agent});
        try{
            if(StringTools.isNotEmpty(agentStr)){
                return Integer.parseInt(agentStr);
            } else {
                return null;
            }
        } catch(Exception ex) {
            return null;
        }
    }
    
    public Boolean isSearchGroup(HttpServletRequest request){
        String searchType = getSearchType(request);
        return SearchTypeEnum.群二维码.getCode().equals(searchType);
    }
    
    public String getSearchType(HttpServletRequest request) {
        return super.getCookie(request, CookieKey.SEARCH_TYPE);
    }
    
    public void setSearchType(HttpServletResponse response, String searchType) {
        super.setCookie(response, CookieKey.SEARCH_TYPE, searchType);
    }
    
    public UserQueryCondition getQuery(HttpServletRequest request) {
        UserQueryCondition queryCondition = new UserQueryCondition();
        String query = super.getCookie(request, CookieKey.QUERY);
        if(StringTools.isNotEmpty(query)){
            try {
                UserDO userDO = JsonTools.fromJson(query, UserDO.class);
                if(userDO!=null){
                    queryCondition = userDO.toQueryCondition();
                }
            } catch (Exception e) {
                //do nothing
            }
        }
        if(isSearchGroup(request)){
            queryCondition.searchGroup();
        } else {
            queryCondition.searchPerson();
        }
        queryCondition.valid().setPageSize(UserQueryCondition.DEFAULT_PAGE_SIZE).setPage(1);
        return queryCondition;
    }
    
    public void setQuery(HttpServletResponse response, UserDO userDO) {
        if(userDO == null){
            userDO = new UserDO();
        }
        super.setCookie(response, CookieKey.QUERY, JsonTools.toJson(userDO));
    }
}
