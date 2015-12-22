package com.fans.biz.manager.impl;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.fans.biz.manager.UserManager;
import com.fans.dal.cache.SystemConfigCache;
import com.fans.dal.dao.UserDAO;
import com.fans.dal.enumerate.SystemConfigKeyEnum;
import com.fans.dal.model.UserDO;
import com.fans.dal.query.UserQueryCondition;
import com.victor.framework.common.tools.DateTools;
import com.victor.framework.common.tools.StringTools;
import com.victor.framework.dal.basic.Paging;

public class UserManagerImpl implements UserManager{

    @Autowired
    private UserDAO userDAO;
    
    @Autowired
    private SystemConfigCache systemConfigCache;
    
    @Override
    public void create(UserDO userDO) {
        String openId = userDO.getOpenId();
        if(StringTools.isNotEmpty(openId) && userDAO.getByOpenId(openId) == null){
            userDAO.insert(userDO);
        }
    }

    @Override
    public void update(UserDO userDO) {
        userDAO.update(userDO);
    }

    @Override
    public void refresh(Long id) {
        UserDO userDO = userDAO.getById(id);
        if(canRefresh(userDO)){
            userDAO.refresh(id);
        }
    }

    @Override
    public UserDO getById(Long id) {
        return userDAO.getById(id);
    }

    @Override
    public UserDO getByOpenId(String openId) {
        UserDO userDO = userDAO.getByOpenId(openId);
        return userDO;
    }

    @Override
    public List<UserDO> getByCondition(UserQueryCondition queryCondition) {
        return userDAO.getByCondition(queryCondition);
    }

    @Override
    public Paging<UserDO> getPage(UserQueryCondition queryCondition) {
        int totalSize = userDAO.getCount(queryCondition);
        @SuppressWarnings("unchecked")
        Paging<UserDO> page = queryCondition.getPaging(totalSize, 5);
        List<UserDO> list = userDAO.getPage(queryCondition);
        page.setData(list);
        return page;
    }

    @Override
    public Boolean canRefresh(UserDO userDO) {
        if(userDO == null || userDO.getGmtRefresh() == null){
            return false;
        }
        Integer interval = systemConfigCache.getCacheInteger(SystemConfigKeyEnum.SYSTEM_REFRESH_INTERVAL.getCode(),10);
        return DateTools.canRefresh(interval, userDO.getGmtRefresh());
    }

    @Override
    public Integer nextRefresh(UserDO userDO) {
        if(userDO == null || userDO.getGmtRefresh() == null){
            return 0;
        }
        Integer interval = systemConfigCache.getCacheInteger(SystemConfigKeyEnum.SYSTEM_REFRESH_INTERVAL.getCode(),10);
        Calendar cal = Calendar.getInstance();
        cal.setTime(userDO.getGmtRefresh());
        cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE) + interval);
        Long countDown = cal.getTime().getTime() - DateTools.today().getTime();
        countDown = countDown / 1000;
        return countDown <= 0 ? 0 : countDown.intValue();
    }

	@Override
	public void stopZhuangB() {
		userDAO.stopZhuangB();
	}
    
}
