package com.fans.admin.model.form;

import com.fans.dal.query.TopupQueryCondition;
import com.victor.framework.common.tools.StringTools;

public class TopupQueryFormBean extends QueryFormBean{
    private String userName;
    private Long userId;
    private Integer type;
    private Integer status;
    private int page = 1;
    
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public int getPage() {
        return page;
    }
    public void setPage(int page) {
        this.page = page;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Integer getType() {
        return type;
    }
    public void setType(Integer type) {
        this.type = type;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    public TopupQueryCondition toQuery(){
        TopupQueryCondition queryCondition = new TopupQueryCondition();
        if(StringTools.isNotEmpty(userName)){
            queryCondition.setUserName(userName);
        }
        if(userId != null){
            queryCondition.setUserId(userId);
        }
        if(type != null){
            queryCondition.setType(type);
        }
        if(status != null){
            queryCondition.setStatus(status);
        }
        queryCondition.setPage(page);
        if(modifyStart!=null){
            queryCondition.setGmtModifyStart(modifyStart);
        }
        if(modifyEnd!=null){
            queryCondition.setGmtModifyEnd(modifyEnd);
        }
        return queryCondition;
    }
}
