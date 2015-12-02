package com.fans.dal.cache.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.InitializingBean;

import com.fans.dal.cache.SystemConfigCache;
import com.fans.dal.model.SystemConfigDO;
import com.fans.dal.query.SystemConfigQueryCondition;
import com.victor.framework.common.tools.DateTools;
import com.victor.framework.dal.cache.StaticCache;

public class SystemConfigCacheImpl extends StaticCache<SystemConfigDO,SystemConfigQueryCondition> implements SystemConfigCache,InitializingBean{

	public SystemConfigCacheImpl() {
		super(SystemConfigDO.class.getSimpleName());
	}

	@Override
	public boolean getSwitch(String key) {
		SystemConfigDO switchDO = this.getCache(key);
		if(switchDO==null){
			return false;
		}
		return ON.equals(switchDO.getConfigValue());
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		reload();
	}

    @Override
    public String getCacheString(String key, String defaultValue) {
        SystemConfigDO switchDO = this.getCache(key);
        if(switchDO==null){
            return defaultValue;
        }
        return switchDO.getConfigValue();
    }

    @Override
    public Integer getCacheInteger(String key, Integer defaultValue) {
        SystemConfigDO switchDO = this.getCache(key);
        if(switchDO==null){
            return defaultValue;
        }
        String valueStr = switchDO.getConfigValue();
        try{
            return Integer.parseInt(valueStr);
        } catch(Exception ex){
            return defaultValue;
        }
    }

    @Override
    public Long getCacheLong(String key, Long defaultValue) {
        SystemConfigDO switchDO = this.getCache(key);
        if(switchDO==null){
            return defaultValue;
        }
        String valueStr = switchDO.getConfigValue();
        try{
            return Long.parseLong(valueStr);
        } catch(Exception ex){
            return defaultValue;
        }
    }

    @Override
    public Date getCacheDate(String key, Date defaultValue) {
        SystemConfigDO switchDO = this.getCache(key);
        if(switchDO==null){
            return defaultValue;
        }
        String valueStr = switchDO.getConfigValue();
        try{
            return DateTools.StringToDate(valueStr);
        } catch(Exception ex){
            return defaultValue;
        }
    }

    @Override
    public Double getCacheDouble(String key, Double defaultValue) {
        SystemConfigDO switchDO = this.getCache(key);
        if(switchDO==null){
            return defaultValue;
        }
        String valueStr = switchDO.getConfigValue();
        try{
            return Double.parseDouble(valueStr);
        } catch(Exception ex){
            return defaultValue;
        }
    }

    @Override
    public BigDecimal getCacheBigDecimal(String key, BigDecimal defaultValue) {
        SystemConfigDO switchDO = this.getCache(key);
        if(switchDO==null){
            return defaultValue;
        }
        String valueStr = switchDO.getConfigValue();
        try{
            return BigDecimal.valueOf(Double.parseDouble(valueStr));
        } catch(Exception ex){
            return defaultValue;
        }
    }
}
