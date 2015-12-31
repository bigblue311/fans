package com.fans.biz.manager.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.fans.biz.manager.UserManager;
import com.fans.dal.cache.SystemConfigCache;
import com.fans.dal.dao.TopListDAO;
import com.fans.dal.dao.UserDAO;
import com.fans.dal.enumerate.SystemConfigKeyEnum;
import com.fans.dal.enumerate.TopListPositionEnum;
import com.fans.dal.model.TopListDO;
import com.fans.dal.model.UserDO;
import com.fans.dal.query.TopListQueryCondition;
import com.fans.dal.query.UserQueryCondition;
import com.google.common.collect.Lists;
import com.victor.framework.common.tools.CollectionTools;
import com.victor.framework.common.tools.DateTools;
import com.victor.framework.common.tools.StringTools;
import com.victor.framework.dal.basic.Paging;

public class UserManagerImpl implements UserManager{

    @Autowired
    private UserDAO userDAO;
    
    @Autowired
    private TopListDAO topListDAO;
    
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
    public void share(Long userId, Integer minutes) {
        TopListDO topListDO = topListDAO.getValidByUserId(userId, TopListPositionEnum.分享.getCode());
        UserDO user = getById(userId);
        if(user == null) {
            return;
        }
        Date expire = DateTools.today();
        if(topListDO!=null && topListDO.getGmtEnd()!=null && topListDO.getGmtEnd().after(expire)){
            topListDAO.expire(userId, TopListPositionEnum.分享.getCode());
        }
        Date gmtEnd = DateTools.addMinute(expire, minutes);
        TopListDO forCreate = new TopListDO();
        forCreate.setGmtStart(expire);
        forCreate.setGmtEnd(gmtEnd);
        forCreate.setUserId(userId);
        forCreate.setOpenId(user.getOpenId());
        forCreate.setPosition(TopListPositionEnum.分享.getCode());
        topListDAO.insert(forCreate);
    }

    @Override
    public List<UserDO> getTopUsers(String openId) {
        UserDO top1 = getRandom(openId, TopListPositionEnum.充值.getCode());
        UserDO top2 = getRandom("", TopListPositionEnum.充值.getCode());
        List<UserDO> result = Lists.newArrayList();
        if(!contains(result, top1)){
            result.add(top1);
        }
        if(!contains(result, top2)){
            result.add(top2);
        }
        return result;
    }
    
    private Boolean contains(List<UserDO> list, UserDO userDO){
        if(userDO == null){
            return true;
        }
        if(CollectionTools.isEmpty(list)){
            return false;
        }
        for(UserDO user : list){
            if(user.getId().intValue() == userDO.getId().intValue()){
                return true;
            }
        }
        return false;
    }
    
    private UserDO getRandom(String openId, Integer position){
        TopListQueryCondition queryCondition = new TopListQueryCondition();
        queryCondition.setValid(0).setPosition(position);
        List<TopListDO> list = topListDAO.getByCondition(queryCondition);
        if(CollectionTools.isEmpty(list)){
            return null;
        }
        for(TopListDO topListDO : list){
            if(topListDO.getOpenId().equals(openId)){
                if(topListDO.getUserId() == null){
                    continue;
                }
                return userDAO.getById(topListDO.getUserId());
            }
        }
        int num = (int)(Math.random() * list.size());
        TopListDO topListDO = list.get(num);
        if(topListDO.getUserId() == null){
            return null;
        }
        return userDAO.getById(topListDO.getUserId());
    }
}
