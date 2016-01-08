package com.fans.web.admin.screen;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Params;
import com.alibaba.fastjson.JSONObject;
import com.fans.admin.model.json.CrumbJson;
import com.fans.biz.manager.TransactionManager;
import com.fans.biz.manager.UserManager;
import com.fans.biz.model.TopupVO;
import com.fans.dal.enumerate.ResourceEnum;
import com.fans.dal.model.UserDO;
import com.fans.dal.query.TopupQueryCondition;
import com.google.common.collect.Lists;
import com.victor.framework.dal.basic.Paging;
import com.fans.dal.enumerate.TopupTypeEnum;
import com.fans.dal.enumerate.TopupStatusEnum;

public class Topup {
    
    @Autowired
    private UserManager userManager;
    
    @Autowired
    private TransactionManager transactionManager;
    
    public void execute(@Params TopupQueryCondition queryCondition,
                        Context context){
        setCrumb(context,queryCondition.getId());
        
        Paging<TopupVO> pageList = Paging.emptyPage();
        pageList = transactionManager.getVOPage(queryCondition);
        
        context.put("query", queryCondition);
        context.put("paging", pageList);
        context.put("list", JSONObject.toJSONString(pageList.getData()));
        context.put("typeEnum", TopupTypeEnum.getAll());
        context.put("statusEnum", TopupStatusEnum.getAll());
    }
    
    private void setCrumb(Context context,Long userId){
        List<CrumbJson> crumbs = Lists.newLinkedList();
        crumbs.add(new CrumbJson(ResourceEnum.交易管理.getName(),ResourceEnum.交易管理.getUri()));
        if(userId != null){
            UserDO userDO = userManager.getById(userId);
            if(userDO != null){
                crumbs.add(new CrumbJson(userDO.getNickName(),ResourceEnum.用户管理.getUri()+"?id="+userId));
            }
        }
        context.put("crumbs", crumbs);
    }
}
