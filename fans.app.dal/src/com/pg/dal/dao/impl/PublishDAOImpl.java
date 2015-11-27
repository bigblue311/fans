package com.pg.dal.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.pg.dal.dao.PublishDAO;
import com.pg.dal.model.PackageDO;
import com.pg.dal.model.PublishDO;
import com.pg.dal.query.PublishQueryCondition;
import com.victor.framework.common.tools.LogTools;
import com.victor.framework.dal.basic.EntityDAO;

public class PublishDAOImpl extends EntityDAO<PublishDO,PublishQueryCondition> implements PublishDAO{

	private static LogTools log = new LogTools(PublishDAOImpl.class);
	
	public PublishDAOImpl() {
		super(PublishDO.class.getSimpleName());
	}

	@Override
	public Boolean softDeleteByPackageId(Long packageId) {
		try {
			getSqlMapClient().update(super.getNamespace()+".softDeleteByPackageId", packageId);
			return true;
		} catch (SQLException e) {
			log.error("SQL执行失败", super.getNamespace()+":"+packageId, e);
			return false;
		}
	}

	public Map<String,String> getEnumMap(){
		Map<String,String> map = Maps.newLinkedHashMap();
		PublishQueryCondition queryCondition = new PublishQueryCondition();
		List<PublishDO> list = this.getByCondition(queryCondition);
		for(PublishDO e: list){
			Object obj = super.getById(PackageDO.class.getSimpleName(), e.getPackageId());
			if(obj == null){
				continue;
			}
			PackageDO packageDO = (PackageDO)obj;
			String value = packageDO.getName();
				   value+= "    [最小预定量: "+(e.getLimitBuyQuantity()==null?"不限":e.getLimitBuyQuantity())+"箱";
				   value+= "    最小预定额: "+(e.getLimitBuyPrice()==null?"不限":e.getLimitBuyPrice())+"元]";
			map.put(getEnumKey(e), value);
		}
		return map;
	}

}
