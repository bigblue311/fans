package com.pg.web.admin.webpage.screen;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.pg.biz.manager.BrandManager;
import com.pg.dal.enumerate.ResourceEnum;
import com.pg.dal.model.BrandDO;
import com.pg.web.admin.model.json.CrumbJson;

public class Brand {
	
	@Autowired
	private BrandManager brandManager;
	
	public void execute(@Param("parentId")Long parentId,
						Context context) {
		setCrumb(context,parentId);
		List<BrandDO> list = Lists.newArrayList();
		if(parentId == null){
			list = brandManager.getTopLevel();
			context.put("parentId", 0l);
		} else {
			list = brandManager.getLevel(parentId);
			context.put("parentId", parentId);
		}
		context.put("list", JSONObject.toJSONString(list));
	}
	
	private void setCrumb(Context context,Long parentId){
		List<CrumbJson> crumbs = Lists.newLinkedList();
		Long prevId = parentId;
		for(int i=0;i<3;i++){
			if(prevId == null || prevId == 0l){
				break;
			} else {
				BrandDO brandDO = brandManager.getById(prevId);
				crumbs.add(new CrumbJson(brandDO.getName(),ResourceEnum.品牌管理.getUri()+"?parentId="+prevId));
				prevId = brandDO.getParentId();
			}
		}
		crumbs.add(new CrumbJson(ResourceEnum.品牌管理.getName(),ResourceEnum.品牌管理.getUri()));
		crumbs = Lists.reverse(crumbs);
		context.put("crumbs", crumbs);
	}
}
