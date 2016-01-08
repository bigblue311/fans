package com.fans.biz.model;

import java.util.Date;

import com.fans.dal.model.TopupDO;
import com.fans.dal.model.UserDO;

public class TopupVO {
    private TopupDO topupDO;
    private UserDO userDO;
    private String operator;
    private String product;
    private Date gmtCreate;
    
    public TopupDO getTopupDO() {
        return topupDO;
    }
    public void setTopupDO(TopupDO topupDO) {
        this.topupDO = topupDO;
    }
    public UserDO getUserDO() {
        return userDO;
    }
    public void setUserDO(UserDO userDO) {
        this.userDO = userDO;
    }
    public String getOperator() {
        return operator;
    }
    public void setOperator(String operator) {
        this.operator = operator;
    }
    public String getProduct() {
        return product;
    }
    public void setProduct(String product) {
        this.product = product;
    }
    public Date getGmtCreate() {
        if(topupDO != null){
            return topupDO.getGmtCreate();
        }
        return gmtCreate;
    }
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}
