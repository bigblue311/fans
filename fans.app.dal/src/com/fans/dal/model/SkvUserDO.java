package com.fans.dal.model;

import java.io.Serializable;

import com.fans.dal.query.SkvUserQueryCondition;
import com.victor.framework.dal.basic.EntityDO;

public class SkvUserDO extends EntityDO implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = -1872234305444923618L;
    private String shoppingLevel;
    private String phone;
    
    public String getShoppingLevel() {
        return shoppingLevel;
    }

    public void setShoppingLevel(String shoppingLevel) {
        this.shoppingLevel = shoppingLevel;
    }
    
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public SkvUserQueryCondition toQueryCondition(){
        SkvUserQueryCondition queryCondition = new SkvUserQueryCondition();
        queryCondition.setQueryMap(this.toMap());
        return queryCondition;
    }
}