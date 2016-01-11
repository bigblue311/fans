package com.fans.web.webpage.screen;

import com.alibaba.citrus.turbine.Context;
import com.fans.biz.threadLocal.RequestSession;
import com.fans.dal.model.UserDO;
import com.fans.web.webpage.RequestSessionBase;

public class My extends RequestSessionBase{

    public void execute(Context context){
    	loadPriceSet(context);
        UserDO userDO = RequestSession.userDO();
        context.put("user", userDO);
    }
}
