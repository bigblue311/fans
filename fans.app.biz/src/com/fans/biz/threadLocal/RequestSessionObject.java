package com.fans.biz.threadLocal;

import com.fans.dal.enumerate.ShoppingLevelEnum;
import com.fans.dal.model.UserDO;
import com.fans.dal.query.UserQueryCondition;

public class RequestSessionObject {
    private UserDO              userDO;
    private ShoppingLevelEnum   level;
    private String              openId;
    private Integer             wxVersion;
    private UserQueryCondition  query;
    
    public UserDO getUserDO() {
        return userDO;
    }
    public void setUserDO(UserDO userDO) {
        this.userDO = userDO;
    }
    public ShoppingLevelEnum getLevel() {
        return level;
    }
    public void setLevel(ShoppingLevelEnum level) {
        this.level = level;
    }
    public String getOpenId() {
        return openId;
    }
    public void setOpenId(String openId) {
        this.openId = openId;
    }
    public Integer getWxVersion() {
        return wxVersion;
    }
    public void setWxVersion(Integer wxVersion) {
        this.wxVersion = wxVersion;
    }
    public UserQueryCondition getQuery() {
        return query;
    }
    public void setQuery(UserQueryCondition query) {
        this.query = query;
    }
}
