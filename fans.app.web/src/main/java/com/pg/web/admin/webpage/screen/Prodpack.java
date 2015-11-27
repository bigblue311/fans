package com.pg.web.admin.webpage.screen;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.google.common.collect.Lists;
import com.pg.biz.manager.ProductManager;
import com.pg.dal.enumerate.ResourceEnum;
import com.pg.dal.model.PackageDO;
import com.pg.dal.model.ProductDO;
import com.pg.dal.query.ProductQueryCondition;
import com.pg.web.admin.model.json.CrumbJson;

public class Prodpack {
	
	@Autowired
	private ProductManager productManager;
	
	public void execute(@Param(name="packageId", defaultValue="1")Long packageId,
						Context context) {
		setCrumb(context,packageId);
		context.put("packageId", packageId);
		List<ProductDO> includeList = getList(packageId,true);
		List<ProductDO> excludeList = getList(packageId,false);
		context.put("includeList", includeList);
		context.put("excludeList", excludeList);
	}
	
	private List<ProductDO> getList(Long packageId, boolean include){
		ProductQueryCondition queryCondition = new ProductQueryCondition();
		if(include){
			queryCondition.setPackageId(packageId);
		} else {
			queryCondition.setNotInPackageId(packageId);
		}
		return productManager.getProductByCondition(queryCondition);
	}
	
	private void setCrumb(Context context, Long packageId){
		List<CrumbJson> crumbs = Lists.newLinkedList();
		crumbs.add(new CrumbJson(ResourceEnum.产品打包.getName(),ResourceEnum.产品打包.getUri()));
		PackageDO packageDO = productManager.getPackageById(packageId);
		crumbs.add(new CrumbJson(packageDO.getName(),ResourceEnum.商品包关系.getUri()+"?packageId="+packageId));
		context.put("crumbs", crumbs);
	}
}
