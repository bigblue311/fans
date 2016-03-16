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
    private String userName;
    private String userPassword;
    private String userImage;
    private String upId;
    
    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getShoppingLevel() {
        return shoppingLevel;
    }

    public void setShoppingLevel(String shoppingLevel) {
        this.shoppingLevel = shoppingLevel;
    }
    
    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getUpId() {
        return upId;
    }

    public void setUpId(String upId) {
        this.upId = upId;
    }

    public SkvUserQueryCondition toQueryCondition(){
        SkvUserQueryCondition queryCondition = new SkvUserQueryCondition();
        queryCondition.setQueryMap(this.toMap());
        return queryCondition;
    }
}
