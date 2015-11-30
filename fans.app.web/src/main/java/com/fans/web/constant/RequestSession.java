package com.fans.web.constant;

import com.fans.dal.model.UserDO;
import com.fans.dal.query.UserQueryCondition;

public class RequestSession {
    private static final ThreadLocal<RequestSessionObject> threadLocal = new ThreadLocal<RequestSessionObject>();

    public static RequestSessionObject get() {
        RequestSessionObject obj = threadLocal.get();
        if(obj == null){
            obj = new RequestSessionObject();
        }
        return obj;
    }
    
    public static void openId(String openId){
        RequestSessionObject obj = get();
        obj.setOpenId(openId);
    }
    
    public static String openId(){
        RequestSessionObject obj = get();
        return obj.getOpenId();
    }
    
    public static void userDO(UserDO userDO){
        RequestSessionObject obj = get();
        obj.setUserDO(userDO);
    }
    
    public static UserDO userDO(){
        RequestSessionObject obj = get();
        return obj.getUserDO();
    }
    
    public static void queryCondition(UserDO userDO){
        RequestSessionObject obj = get();
        if(userDO == null){
            userDO = new UserDO();
        }
        UserQueryCondition queryCondition = userDO.toQueryCondition();
        queryCondition.valid().setPage(1);
        obj.setQuery(queryCondition);
    }
    
    public static void queryCondition(UserQueryCondition queryCondition){
        RequestSessionObject obj = get();
        if(queryCondition == null){
            queryCondition = new UserQueryCondition();
        }
        queryCondition.valid().setPage(1);
        obj.setQuery(queryCondition);
    }
    
    public static UserQueryCondition queryCondition(){
        RequestSessionObject obj = get();
        UserQueryCondition queryCondition = obj.getQuery();
        if(queryCondition == null){
            queryCondition = new UserQueryCondition();
            queryCondition.valid().setPage(1);
            obj.setQuery(queryCondition);
        }
        return queryCondition;
    }
}