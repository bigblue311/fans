package com.fans.admin.model.form;

import com.fans.dal.query.UserQueryCondition;
import com.victor.framework.common.tools.StringTools;


public class UserQueryFormBean extends QueryFormBean{
    private Long id;
    private String nickName;
    private String phone;
    private String province;
    private String city;
    private int page = 1;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNickName() {
        return nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getProvince() {
        return province;
    }
    public void setProvince(String province) {
        this.province = province;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public int getPage() {
        return page;
    }
    public void setPage(int page) {
        this.page = page;
    }
    
    public UserQueryCondition toQuery(){
        UserQueryCondition queryCondition = new UserQueryCondition();
        if(id!=null){
            queryCondition.setId(id);
        }
        if(StringTools.isNotEmpty(nickName)){
            queryCondition.setNickName(nickName);
        }
        if(StringTools.isNotEmpty(phone)){
            queryCondition.setPhone(phone);
        }
        if(StringTools.isNotEmpty(province)){
            queryCondition.setProvince(province);
        }
        if(StringTools.isNotEmpty(city)){
            queryCondition.setCity(city);
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
