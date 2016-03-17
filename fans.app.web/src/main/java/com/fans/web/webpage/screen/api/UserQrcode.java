package com.fans.web.webpage.screen.api;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.fans.biz.manager.UserManager;
import com.fans.dal.model.UserDO;
import com.victor.framework.common.shared.Result;
import com.victor.framework.common.tools.StringTools;

public class UserQrcode {
    
    @Autowired
    private UserManager userManager;
    
    public Result<String> execute(@Param("id") Long id){
        UserDO userDO = userManager.getBySkvId(id);
        if(userDO == null){
            return Result.newInstance(null, "用户不存在", false);
        }
        if(StringTools.isEmpty(userDO.getQrcode())){
            return Result.newInstance(null, "用户微信二维码未上传", false);
        }
        return Result.newInstance(userDO.getQrcode(), "成功", true);
    }
}
