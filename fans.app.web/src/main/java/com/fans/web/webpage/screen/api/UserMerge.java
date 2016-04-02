package com.fans.web.webpage.screen.api;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.fans.biz.manager.UserManager;
import com.fans.dal.model.SkvUserDO;
import com.fans.dal.model.UserDO;
import com.fans.web.webpage.RequestSessionBase;
import com.victor.framework.common.shared.Result;

public class UserMerge extends RequestSessionBase{
    
    @Autowired
    private UserManager userManager;
    
    @Autowired
    private HttpServletRequest request;
    
    public Result<Boolean> execute(@Param("phone") String phone,
                                   @Param("password") String password){
        UserDO userDO = super.getUserDO(request);
        SkvUserDO skvUserDO = userManager.getSkvUserByPassword(phone, password);
        if(skvUserDO == null){
            return Result.newInstance(false, "用户名或密码错误", false);
        }
        Long skvId = skvUserDO.getId();
        userManager.merge(userDO.getId(), skvId);
        userManager.mergeSkvUser(phone, userDO.getOpenId());
        
        return Result.newInstance(true, "绑定账号成功", true);
    }
}
