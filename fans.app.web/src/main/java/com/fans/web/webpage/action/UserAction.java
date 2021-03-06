package com.fans.web.webpage.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.FormGroup;
import com.aliyun.service.FileStorageRepository;
import com.fans.biz.manager.UserManager;
import com.fans.dal.model.UserDO;
import com.fans.web.webpage.RequestSessionBase;
import com.victor.framework.common.tools.StringTools;

public class UserAction extends RequestSessionBase{
    
    @Autowired
    private UserManager userManager;
    
    @Autowired
    private FileStorageRepository fileStorageRepository;
    
    @Autowired
    private HttpServletRequest request;
    
    @Autowired
    private HttpServletResponse response;
    
    public void doQuery(@FormGroup("user") UserDO userDO){
        if(userDO == null){
            userDO = new UserDO();
        }
        super.setQuery(response, userDO);
    }
    
    public void doUpdate(@FormGroup("user") UserDO userDO){
        if(StringTools.isEmpty(userDO.getOpenId())){
            userDO.setOpenId(super.getOpenId(request));
        }
        String domain = super.getDomain(request);
        userDO.setDomain(domain);
        if(userDO.getId() == null){
            userManager.create(userDO);
        } else {
            if(StringTools.isNotEmpty(userDO.getPhone())){
                //假如修改了手机号码, 那么要合并
                userManager.mergeSkvUser(userDO.getPhone(), userDO.getOpenId());
            }
            userManager.update(userDO);
        }
    }
    
    public void doRefresh(@FormGroup("user") UserDO userDO){
        userManager.refresh(userDO.getId());
    }
}
