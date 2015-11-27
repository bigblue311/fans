package com.pg.web.admin.webpage.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.FormGroup;
import com.pg.dal.cache.NewsCache;
import com.pg.dal.model.NewsDO;

public class NewsAction {
	
	@Autowired
	private NewsCache newsCache;
	
	public void doUpdate(@FormGroup("systemNews") NewsDO newsDO) {
		if(newsDO.getId() == null ) {
			newsCache.insertDB(newsDO);
		} else {
			newsCache.updateDB(newsDO);
		}
	}
	
	public void doDelete(@FormGroup("systemNews") NewsDO newsDO) {
		newsCache.deleteDB(newsDO.getId());
	}
}
