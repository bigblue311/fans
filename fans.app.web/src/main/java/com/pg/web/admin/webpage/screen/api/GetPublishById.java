package com.pg.web.admin.webpage.screen.api;


import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.pg.biz.manager.ProductManager;
import com.pg.biz.model.PackageVO;
import com.victor.framework.common.shared.Result;

public class GetPublishById {
	
	@Autowired
	private ProductManager productManager;
	
	public Result<PackageVO> execute(@Param("id")Long id) {
		PackageVO packageVO = productManager.getPackageVOByPublishId(id);
		return Result.newInstance(packageVO, "获取数据成功", packageVO!=null);
	}
}
