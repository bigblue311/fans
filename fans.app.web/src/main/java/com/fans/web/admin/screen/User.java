package com.fans.web.admin.screen;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Params;
import com.alibaba.fastjson.JSONObject;
import com.fans.admin.model.form.UserQueryFormBean;
import com.fans.admin.model.json.CrumbJson;
import com.fans.biz.manager.UserManager;
import com.fans.dal.enumerate.ResourceEnum;
import com.fans.dal.model.UserDO;
import com.fans.dal.query.UserQueryCondition;
import com.google.common.collect.Lists;
import com.victor.framework.dal.basic.Paging;
import com.fans.dal.enumerate.GenderEnum;

public class User {
    
    @Autowired
    private UserManager userManager;
    
    public void execute(@Params UserQueryFormBean userQueryFormBean,
                        Context context){
        UserQueryCondition queryCondition = userQueryFormBean.toQuery();
        
        setCrumb(context,queryCondition.getId());
        
        Paging<UserDO> pageList = Paging.emptyPage();
        pageList = userManager.getPage(queryCondition);
        
        context.put("query", queryCondition);
        context.put("paging", pageList);
        context.put("list", JSONObject.toJSONString(pageList.getData()));
        context.put("genderEnum", GenderEnum.getAll());
    }
    
    private void setCrumb(Context context,Long id){
        List<CrumbJson> crumbs = Lists.newLinkedList();
        crumbs.add(new CrumbJson(ResourceEnum.用户管理.getName(),ResourceEnum.用户管理.getUri()));
        if(id != null){
            UserDO userDO = userManager.getById(id);
            if(userDO != null){
                crumbs.add(new CrumbJson(userDO.getNickName(),ResourceEnum.用户管理.getUri()+"?id="+id));
            }
        }
        context.put("crumbs", crumbs);
    }
}
