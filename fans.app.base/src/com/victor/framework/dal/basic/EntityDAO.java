package com.victor.framework.dal.basic;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.victor.framework.annotation.EnumKey;
import com.victor.framework.annotation.EnumValue;
import com.victor.framework.common.tools.LogTools;

public class EntityDAO<Entity extends EntityDO, Query extends QueryCondition> {
	private static LogTools log = new LogTools(EntityDAO.class);
	
	private String namespace = "";
	private MySqlMapClient mySqlMapClient;
	
	public String getNamespace() {
		return namespace;
	}
	
	public void setMySqlMapClient(MySqlMapClient mySqlMapClient) {
		this.mySqlMapClient = mySqlMapClient;
	}

	public EntityDAO(String namespace){
		this.namespace = namespace;
	}
	
	public SqlMapClient getSqlMapClient(){
		return mySqlMapClient.getSqlMapClient();
	}
	
	@SuppressWarnings("unchecked")
	public List<Entity> getAll(){
		try {
			Object obj = getSqlMapClient().queryForList(namespace+".getAll");
			if(obj != null) {
				List<Entity> list = (List<Entity>)obj;
				return list;
			} else {
				return Lists.newArrayList();
			}
		} catch (SQLException e) {
			log.error("SQL执行失败", "getAll", e);
			return Lists.newArrayList();
		}
	}
	
	public Map<String,String> getEnumMap(){
		Map<String,String> map = Maps.newLinkedHashMap();
		for(Entity e: getAll()){
			map.put(getEnumKey(e), getEnumValue(e));
		}
		return map;
	}
	
	protected String getEnumKey(Entity e){
		Field[] fields = e.getClass().getDeclaredFields();
		
		for(Field field : fields){
			EnumKey key = field.getAnnotation(EnumKey.class);
			if(key != null){
				return getFiledValue(e,field);
			}
		}
		return e.getId().toString();
	}
	
	protected String getEnumValue(Entity e){
		Field[] fields = e.getClass().getDeclaredFields();
		
		for(Field field : fields){
			EnumValue key = field.getAnnotation(EnumValue.class);
			if(key != null){
				return getFiledValue(e,field);
			}
		}
		return e.getId().toString();
	}
	
	protected String getFiledValue(Entity e,Field field){
		Method[] methods = e.getClass().getDeclaredMethods();
		for(Method method : methods){
			if(method.getName().equalsIgnoreCase("get"+field.getName())){
				try {
					Object obj = method.invoke(e, new Object[0]);
					if(obj == null){
						return "";
					}
					return obj.toString();
				} catch (IllegalAccessException e1) {
					return e.getId().toString();
				} catch (IllegalArgumentException e1) {
					return e.getId().toString();
				} catch (InvocationTargetException e1) {
					return e.getId().toString();
				}
			}
		}
		return e.getId().toString();
	}
	
	public List<Entity> queryForList(String sqlId, Entity query){
		return queryForList(sqlId, query.toMap());
	}
	
	public List<Object> queryForList(String namespace, String sqlId, EntityDO query){
		return queryForList(namespace, sqlId, query.toMap());
	}
	
	public List<Entity> queryForList(String sqlId, String key, Object query){
		try {
			Object obj = getSqlMapClient().queryForList(namespace+"."+sqlId, query);
			if(obj != null) {
				@SuppressWarnings("unchecked")
				List<Entity> list = (List<Entity>)obj;
				return list;
			} else {
				return Lists.newArrayList();
			}
		} catch (SQLException e) {
			log.error("SQL执行失败", query, e);
			return Lists.newArrayList();
		}
	}
	
	public List<Object> queryForList(String namespace, String sqlId, String key, Object query){
		Map<String,Object> queryMap = Maps.newHashMap();
		queryMap.put(key, query);
		return queryForList(namespace,sqlId,queryMap);
	}
	
	public List<Entity> queryForList(String sqlId){
		return queryForList(sqlId,new HashMap<String,Object>());
	}
	
	@SuppressWarnings("unchecked")
	public List<Object> queryForList(String namespace, String sqlId){
		try {
			Object obj = getSqlMapClient().queryForList(namespace+"."+sqlId);
			if(obj != null) {
				List<Object> list = (List<Object>)obj;
				return list;
			} else {
				return Lists.newArrayList();
			}
		} catch (SQLException e) {
			log.error("SQL执行失败", e);
			return Lists.newArrayList();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Entity> queryForList(String sqlId, Map<String,Object> query){
		try {
			Object obj = getSqlMapClient().queryForList(namespace+"."+sqlId, query);
			if(obj != null) {
				List<Entity> list = (List<Entity>)obj;
				return list;
			} else {
				return Lists.newArrayList();
			}
		} catch (SQLException e) {
			log.error("SQL执行失败", query, e);
			return Lists.newArrayList();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Object> queryForList(String namespace, String sqlId, Map<String,Object> query){
		try {
			Object obj = getSqlMapClient().queryForList(namespace+"."+sqlId, query);
			if(obj != null) {
				List<Object> list = (List<Object>)obj;
				return list;
			} else {
				return Lists.newArrayList();
			}
		} catch (SQLException e) {
			log.error("SQL执行失败", query, e);
			return Lists.newArrayList();
		}
	}
	
	@SuppressWarnings("unchecked")
	public Entity queryForEntity(String sqlId,Entity query){
		return (Entity)queryForEntity(this.namespace, sqlId,query.toMap());
	}
	
	public Object queryForEntity(String namespace, String sqlId,EntityDO query){
		return queryForEntity(namespace, sqlId, query.toMap());
	}
	
	public Entity queryForEntity(String sqlId, String key, Object query){
		Map<String,Object> queryMap = Maps.newHashMap();
		queryMap.put(key, query);
		return queryForEntity(sqlId,queryMap);
	}
	
	public Object queryForEntity(String namespace, String sqlId, String key, Object query){
		Map<String,Object> queryMap = Maps.newHashMap();
		queryMap.put(key, query);
		return queryForEntity(namespace,sqlId,queryMap);
	}
	
	@SuppressWarnings("unchecked")
	public Entity queryForEntity(String sqlId){
		try {
			Object obj = getSqlMapClient().queryForObject(namespace+"."+sqlId);
			if(obj != null) {
				return (Entity)obj;
			} else {
				return null;
			}
		} catch (SQLException e) {
			log.error("SQL执行失败", e);
			return null;
		}
	}
	
	public Object queryForEntity(String namespace, String sqlId){
		try {
			Object obj = getSqlMapClient().queryForObject(namespace+"."+sqlId);
			if(obj != null) {
				return obj;
			} else {
				return null;
			}
		} catch (SQLException e) {
			log.error("SQL执行失败", e);
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public Entity queryForEntity(String sqlId, Map<String,Object> query){
		try {
			Object obj = getSqlMapClient().queryForObject(namespace+"."+sqlId, query);
			if(obj != null) {
				return (Entity)obj;
			} else {
				return null;
			}
		} catch (SQLException e) {
			log.error("SQL执行失败", query, e);
			return null;
		}
	}
	
	public Object queryForEntity(String namespace, String sqlId, Map<String,Object> query){
		try {
			Object obj = getSqlMapClient().queryForObject(namespace+"."+sqlId, query);
			if(obj != null) {
				return obj;
			} else {
				return null;
			}
		} catch (SQLException e) {
			log.error("SQL执行失败", query, e);
			return null;
		}
	}

	public Long insert(Entity entity){
		return insert(this.namespace,entity);
	}
	
	public Long insert(String namespace, EntityDO entity){
		try {
			Long id = (Long)getSqlMapClient().insert(namespace+".insert",entity);
			return id;
		} catch (SQLException e) {
			log.error("SQL执行失败", entity, e);
			return null;
		}
	}
	
	public Boolean update(Entity entity){
		return update(this.namespace,entity);
	}
	
	public Boolean update(String sqlId){
		try {
			getSqlMapClient().update(this.namespace+"."+sqlId);
			return true;
		} catch (SQLException e) {
			log.error("SQL执行失败", e);
			return false;
		}
	}
	
	public Boolean updateBySID(String sqlId,Entity entity){
		return updateBySID(this.namespace,sqlId,entity);
	}
	
	public Boolean update(String namespace, EntityDO entity){
		try {
			getSqlMapClient().update(namespace+".update",entity);
			return true;
		} catch (SQLException e) {
			log.error("SQL执行失败", entity, e);
			return false;
		}
	}
	
	public Boolean updateBySID(String namespace,String sqlId,Entity entity){
		try {
			getSqlMapClient().update(namespace+"."+sqlId,entity);
			return true;
		} catch (SQLException e) {
			log.error("SQL执行失败", entity, e);
			return false;
		}
	}
	
	public Boolean delete(Long id){
		return delete(this.namespace,id);
	}
	
	public Boolean delete(String namespace, Long id){
		try {
			getSqlMapClient().delete(namespace+".deleteById", id);
			return true;
		} catch (SQLException e) {
			log.error("SQL执行失败", namespace+":"+id, e);
			return false;
		}
	}
	
	public Boolean deleteBySID(String sqlId, Long id){
		try {
			getSqlMapClient().delete(namespace+"."+sqlId, id);
			return true;
		} catch (SQLException e) {
			log.error("SQL执行失败", namespace+":"+id, e);
			return false;
		}
	}
	
	public Boolean deleteBySID(String namespace, String sqlId, Long id){
		try {
			getSqlMapClient().delete(namespace+"."+sqlId, id);
			return true;
		} catch (SQLException e) {
			log.error("SQL执行失败", namespace+":"+id, e);
			return false;
		}
	}
	
	public Boolean softDelete(Long id){
		return softDelete(this.namespace,id);
	}
	
	public Boolean softDelete(String namespace, Long id){
		try {
			getSqlMapClient().update(namespace+".softDeleteById", id);
			return true;
		} catch (SQLException e) {
			log.error("SQL执行失败", namespace+":"+id, e);
			return false;
		}
	}
	
	public Boolean recover(Long id){
		return recover(this.namespace,id);
	}
	
	public Boolean recover(String namespace, Long id){
		try {
			getSqlMapClient().update(namespace+".recoverById", id);
			return true;
		} catch (SQLException e) {
			log.error("SQL执行失败", namespace+":"+id, e);
			return false;
		}
	}
	
	public Boolean recycle(Long[] id){
		return recycle(this.namespace,id);
	}
	
	public Boolean recycle(String namespace,Long[] id){
		try {
			getSqlMapClient().delete(namespace+".recycle",id);
			return true;
		} catch (SQLException e) {
			log.error("SQL执行失败", namespace, e);
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	public Entity getById(Long id){
		try {
			Object obj = getSqlMapClient().queryForObject(namespace+".getById", id);
			if(obj != null) {
				return (Entity)obj;
			} else {
				return null;
			}
		} catch (SQLException e) {
			log.error("SQL执行失败", namespace+":"+id, e);
			return null;
		}
	}
	
	public Object getById(String namespace,Long id){
		try {
			Object obj = getSqlMapClient().queryForObject(namespace+".getById", id);
			if(obj != null) {
				return obj;
			} else {
				return null;
			}
		} catch (SQLException e) {
			log.error("SQL执行失败", namespace+":"+id, e);
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public Entity getByUUId(String uuid){
		try {
			Object obj = getSqlMapClient().queryForObject(namespace+".getByUUId", uuid);
			if(obj != null) {
				return (Entity)obj;
			} else {
				return null;
			}
		} catch (SQLException e) {
			log.error("SQL执行失败", namespace+":"+uuid, e);
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Entity> getByIds(Long[] id){
		List<Entity> list = Lists.newArrayList();
		try {
			Object obj = getSqlMapClient().queryForList(namespace+".getByIds", id);
			if(obj != null) {
				list = (List<Entity>)obj;
				return list;
			} else {
				return list;
			}
		} catch (SQLException e) {
			log.error("SQL执行失败", namespace+":"+id, e);
			return list;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Object> getByIds(String namespace,Long[] id){
		List<Object> list = Lists.newArrayList();
		try {
			Object obj = getSqlMapClient().queryForList(namespace+".getByIds", id);
			if(obj != null) {
				list = (List<Object>)obj;
				return list;
			} else {
				return list;
			}
		} catch (SQLException e) {
			log.error("SQL执行失败", namespace+":"+id, e);
			return list;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Entity> getByCondition(Query query){
		List<Entity> list = Lists.newArrayList();
		try {
			Object obj = getSqlMapClient().queryForList(namespace+".getByCondition", query.getQueryMap());
			if(obj != null) {
				list = (List<Entity>)obj;
				return list;
			} else {
				return list;
			}
		} catch (SQLException e) {
			log.error("SQL执行失败", namespace+":"+query, e);
			return list;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Entity> getPage(Query query){
		List<Entity> list = Lists.newArrayList();
		try {
			Object obj = getSqlMapClient().queryForList(namespace+".getPage", query.getQueryMap());
			if(obj != null) {
				list = (List<Entity>)obj;
				return list;
			} else {
				return list;
			}
		} catch (SQLException e) {
			log.error("SQL执行失败", namespace+":"+query, e);
			return list;
		}
	}
	
	public Integer getCount(Query query){
		try {
			Object obj = getSqlMapClient().queryForObject(namespace+".getCount", query.getQueryMap());
			if(obj != null) {
				return (Integer)obj;
			} else {
				return 0;
			}
		} catch (SQLException e) {
			log.error("SQL执行失败", query, e);
			return 0;
		}
	}
}
