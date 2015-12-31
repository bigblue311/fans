package com.victor.framework.dal.cache;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.victor.framework.common.tools.JsonTools;

public class GuavaCache {
    
    private LoadingCache<String,String> localCache = null;
    
    public GuavaCache(long expire){
        localCache=CacheBuilder.newBuilder().expireAfterWrite(7000, TimeUnit.SECONDS).build(new CacheLoader<String, String>(){
            @Override
            public String load(String key) throws Exception {        
                return "N/A";
            }
        });
    }
    
    public GuavaCache(long expire, TimeUnit timeUnit){
        localCache=CacheBuilder.newBuilder().expireAfterWrite(7000, timeUnit).build(new CacheLoader<String, String>(){
            @Override
            public String load(String key) throws Exception {        
                return "N/A";
            }
        });
    }
    
    public String get(String key){
        String result;
        try {
            result = localCache.get(key);
            if("N/A".equals(result)){
                return null;
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }
    
    public void put(String key, String value){
        if(value != null){
            localCache.put(key, value);
        }
    }
    
    public void putObject(String key, Object obj){
        String json = JsonTools.toJson(obj);
        put(key,json);
    }
    
    public <T> T getObject(String key, Class<T> clazz){
        String json = get(key);
        if(json == null){
            return null;
        }
        return JsonTools.fromJson(json, clazz);
    }
}
