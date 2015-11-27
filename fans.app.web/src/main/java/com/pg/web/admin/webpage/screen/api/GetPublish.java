package com.pg.web.admin.webpage.screen.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Params;
import com.google.common.collect.Lists;
import com.pg.biz.manager.ProductManager;
import com.pg.biz.model.PackageVO;
import com.pg.dal.query.PublishQueryCondition;
import com.victor.framework.common.shared.Result;

public class GetPublish {
	
	@Autowired
	private ProductManager productManager;
	
	public Result<List<PackageVO>> execute(@Params PublishQueryCondition queryCondition) {
		List<PackageVO> list = Lists.newArrayList();
		queryCondition.setValid(true);
		list = productManager.getPackageVOByCondition(queryCondition);
		return Result.newInstance(list, "获取数据成功", !list.isEmpty());
	}
}
