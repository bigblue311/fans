package com.fans.web.webpage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        queryCondition.valid().setPage(1);
        return queryCondition;
    }
    
    public void setQuery(HttpServletResponse response, UserDO userDO) {
        if(userDO == null){
            userDO = new UserDO();
        }
        super.setCookie(response, CookieKey.QUERY, JsonTools.toJson(userDO));
    }
}
