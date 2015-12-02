package com.fans.web.webpage.screen;

import com.alibaba.citrus.turbine.Context;
import com.fans.dal.model.UserDO;
import com.fans.web.constant.RequestSession;

public class My {

    public void execute(Context context){
        UserDO userDO = RequestSession.userDO();
        context.put("user", userDO);
    }
}
