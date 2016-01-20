package com.fans.web.pipeline;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.pipeline.PipelineContext;
import com.alibaba.citrus.service.pipeline.Valve;
import com.fans.biz.manager.UserManager;
import com.fans.biz.threadLocal.RequestSession;
import com.fans.dal.cache.SystemConfigCache;
import com.fans.dal.enumerate.ShoppingLevelEnum;
import com.fans.dal.enumerate.SystemConfigKeyEnum;
import com.fans.dal.model.SkvUserDO;
import com.fans.dal.model.UserDO;
import com.fans.dal.query.UserQueryCondition;
import com.fans.web.webpage.RequestSessionBase;
import com.victor.framework.common.tools.DateTools;
import com.victor.framework.common.tools.StringTools;

public class RequestSessionValve extends RequestSessionBase implements Valve {

	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private HttpServletResponse response;
	
	@Autowired
	private UserManager userManager;
	
	@Autowired
    private SystemConfigCache systemConfigCache;
	
	@Override
	public void invoke(PipelineContext pipelineContext) throws Exception {
		try {
		    String domain = super.getDomain(request);
		    String openId = super.getOpenId(request);
		    Long skvId = super.getSkvId(request);
		    if(systemConfigCache.getSwitch(SystemConfigKeyEnum.DEBUG_MODE.getCode())){
		        if(StringTools.isEmpty(openId)){
		            openId = "ogOTHwaJi6KDLOjDu-59Nze0YW8M";
		            super.setOpenId(response, openId);
		        }
		        if(skvId == null){
		            skvId = 1l;
		        }
            }
		    //setOpenId
		    RequestSession.openId(openId);
		    
		    SkvUserDO skvUser = userManager.getSkvUserById(skvId);
		    UserDO userDO = userManager.getByOpenId(openId);
		    if(userDO == null){
		        userDO = userManager.getByExtId(skvId);
		        if(userDO == null && skvUser != null){
		            userDO = new UserDO();
		            userDO.setOpenId(openId);
		            userDO.setHeadImg("http://wetuan.com/xls/"+skvUser.getUserImage());
		            userDO.setNickName(skvUser.getUserName());
		            userDO.setCoins(systemConfigCache.getCacheInteger(SystemConfigKeyEnum.NEW_COINS.getCode(), 0));
		            Integer dayToAdd = systemConfigCache.getCacheInteger(SystemConfigKeyEnum.NEW_VIP.getCode(), 0);
		            Date vipExpire = DateTools.addDate(DateTools.today(), dayToAdd);
		            userDO.setGmtVipExpire(vipExpire);
		            userDO.setDomain(domain);
		            userManager.create(userDO);
		        }
		    }
		    if(userDO != null && userDO.getSkvId() == null && skvUser != null){
		        userDO.setSkvId(skvId);
		        userDO.setPhone(skvUser.getPhone()); 
		        userManager.update(userDO);
		    } 
		    if(userDO != null && userDO.getSkvId() != null) {
		        skvUser = userManager.getSkvUserById(userDO.getSkvId());
		        if(skvUser != null){
		            String shoppingLevel = skvUser.getShoppingLevel();
	                ShoppingLevelEnum level = ShoppingLevelEnum.getByCode(shoppingLevel);
	                //setLevel
	                RequestSession.level(level);
		        } else {
	                RequestSession.level(null);
	            }
		    } else {
		        RequestSession.level(null);
		    }
		    
		    //setUser
		    RequestSession.userDO(userDO);
		    
		    UserQueryCondition queryCondition = super.getQuery(request);
		    RequestSession.queryCondition(queryCondition);
		} finally {
			pipelineContext.invokeNext();
		}
	}
}
