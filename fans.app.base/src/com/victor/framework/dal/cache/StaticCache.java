package com.victor.framework.dal.cache;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.victor.framework.annotation.StaticCacheKey;
import com.victor.framework.common.shared.Split;
import com.victor.framework.common.tools.StringTools;
import com.victor.framework.dal.basic.EntityDAO;
import com.victor.framework.dal.basic.EntityDO;
import com.victor.framework.dal.basic.QueryCondition;

public abstract class StaticCache<entity extends EntityDO, query extends QueryCondition> extends EntityDAO<entity,query> {
	
	public StaticCache(String namespace) {
		super(namespace);
	}

	public static final String ON = "ON";
	private final Map<String,entity> cacheMap = Maps.newConcurrentMap();
	
	public void reload(){
		List<entity> all = super.getAll();
		for(entity e : all){
			updateCache(getKey(e),e);
		}
	}
	
	public final void clearCache(){
		cacheMap.clear();
	}
	
	public final entity getCache(String key){
		if(key == null) return null;
		return cacheMap.get(key);
	}
	
	public final boolean exist(String key){
		if(key == null) return false;
		return cacheMap.containsKey(key);
	}
	
	public final void updateCache(String key, entity value){
		cacheMap.put(key, value);
	}
	
	public final void updateDB(entity e){
		super.update(e);
		entity forUpdate = super.getById(e.getId());
		updateCache(getKey(forUpdate),forUpdate);
	}
	
	public final void insertDB(entity e){
		Long id = super.insert(e);
		entity forUpdate = super.getById(id);
		updateCache(getKey(forUpdate),forUpdate);
	}
	
	public final void deleteDB(Long id){
		entity forUpdate = super.getById(id);
		cacheMap.remove(getKey(forUpdate));
		super.delete(id);
	}
	
	public final List<entity> cacheValues(){
		List<entity> list = Lists.newArrayList();
		for(entity e : cacheMap.values()){
			list.add(e);
		}
		return list;
	}
	
	@Override
	public Map<String,String> getEnumMap(){
		Map<String,String> map = Maps.newLinkedHashMap();
		for(entity e: cacheMap.values()){
			map.put(getEnumKey(e), getEnumValue(e));
		}
		return map;
	}
	
	protected String getKey(entity e){
		Field[] fields = e.getClass().getDeclaredFields();
		List<String> result = Lists.newArrayList();
		for(Field field : fields){
			StaticCacheKey key = field.getAnnotation(StaticCacheKey.class);
			if(key != null){
				String filedValue = getFiledValue(e,field);
				if(StringTools.isNotEmpty(filedValue)){
					result.add(filedValue);
				}
			}
		}
		if(result.isEmpty()){
			return e.getId().toString();
		} else {
			return StringTools.join(Split.逗号, result.toArray());
		}
		
	}
}
