package com.fans.web.webpage.screen.api;


import org.springframework.beans.factory.annotation.Autowired;

import com.fans.dal.cache.LocationCache;
import com.fans.dal.cache.SystemConfigCache;
import com.victor.framework.common.shared.Result;

public class CacheService {
    
    @Autowired
    private SystemConfigCache systemConfigCache;
    
    @Autowired
    private LocationCache locationCache;
    
    public Result<String> reload(){
        systemConfigCache.reload();
        locationCache.reload();
        return Result.newInstance("", "刷新缓存成功", true);
    }
}
