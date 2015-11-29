package com.fans.web.webpage.screen;

import com.alibaba.citrus.turbine.Context;

public class Index {
    public void execute(Context context){
        context.put("hello", "hello");
    }
}
