package com.fans.web.webpage.action;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.FormGroup;
import com.fans.biz.manager.FileUploadManager;
import com.fans.biz.manager.UserManager;
import com.fans.dal.model.UserDO;
import com.fans.web.constant.RequestSession;
import com.fans.web.webpage.RequestSessionBase;
import com.victor.framework.common.shared.Result;
import com.victor.framework.common.tools.StringTools;

public class UserAction extends RequestSessionBase{
    
    @Autowired
    private UserManager userManager;
    
    @Autowired
    private FileUploadManager fileUploadManager;
    
    @Autowired
    private HttpServletResponse response;
    
    public void doQuery(@FormGroup("user") UserDO userDO){
        if(userDO == null){
            userDO = new UserDO();
        }
        RequestSession.queryCondition(userDO);
        super.setQuery(response, userDO);
    }
    
    public void doUpdate(@FormGroup("user") UserDO userDO){
        if(StringTools.isNotEmpty(userDO.getHeadImg()) && userDO.getHeadImg().contains("temp/")){
            Result<String> result = fileUploadManager.copyTemp(userDO.getHeadImg());
            if(result.isSuccess()){
                userDO.setHeadImg(result.getDataObject());
            } else {
                userDO.setHeadImg("");
            }
        }
        if(StringTools.isNotEmpty(userDO.getQrcode()) && userDO.getQrcode().contains("temp/")){
            Result<String> result = fileUploadManager.copyTemp(userDO.getQrcode());
            if(result.isSuccess()){
                userDO.setQrcode(result.getDataObject());
            } else {
                userDO.setQrcode("");
            }
        }
        userManager.update(userDO);
    }
    
    public void doRefresh(@FormGroup("user") UserDO userDO){
        userManager.refresh(userDO.getId());
    }
}
