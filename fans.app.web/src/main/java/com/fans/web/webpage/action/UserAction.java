package com.fans.web.webpage.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.FormGroup;
import com.aliyun.service.FileStorageRepository;
import com.fans.biz.manager.UserManager;
import com.fans.biz.threadLocal.RequestSession;
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
            userManager.update(userDO);
        }
        updateRequestSession();
    }
    
    public void doRefresh(@FormGroup("user") UserDO userDO){
        userManager.refresh(userDO.getId());
        updateRequestSession();
    }
    
    private void updateRequestSession(){
        String openId = super.getOpenId(request);
        Long skvId = super.getSkvId(request);
        UserDO userDO = userManager.getByOpenId(openId);
        if(userDO == null){
            userDO = userManager.getBySkvId(skvId);
        }
        RequestSession.userDO(userDO);
    }
}
