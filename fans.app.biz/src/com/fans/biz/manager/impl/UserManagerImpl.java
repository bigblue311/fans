package com.fans.biz.manager.impl;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.fans.biz.manager.UserManager;
import com.fans.dal.cache.SystemConfigCache;
import com.fans.dal.dao.SkvUserDAO;
import com.fans.dal.dao.TopListDAO;
import com.fans.dal.dao.UserDAO;
import com.fans.dal.enumerate.SystemConfigKeyEnum;
import com.fans.dal.enumerate.TopListPositionEnum;
import com.fans.dal.model.SkvUserDO;
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
    private SkvUserDAO skvUserDAO;
    
    @Autowired
    private TopListDAO topListDAO;
    
    @Autowired
    private SystemConfigCache systemConfigCache;
    
    @Override
    public void create(UserDO userDO) {
        String openId = userDO.getOpenId();
        Long extId = userDO.getExtId();
        if(StringTools.isNotEmpty(openId)){
            if(userDAO.getByOpenId(openId) == null) {
                userDAO.insert(userDO);
            }
        } 
        if(extId!=null){
            if(userDAO.getByExtId(extId) == null){
                userDAO.insert(userDO);
            }
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
    public SkvUserDO getSkvUserById(Long id) {
        if(id == null) {
            return null;
        }
        try {
            return skvUserDAO.getById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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
        Integer interval = systemConfigCache.getCacheInteger(SystemConfigKeyEnum.REFRESH_INTERVAL.getCode(),10);
        return DateTools.canRefresh(interval, userDO.getGmtRefresh());
    }

    @Override
    public Integer nextRefresh(UserDO userDO) {
        if(userDO == null || userDO.getGmtRefresh() == null){
            return 0;
        }
        Integer interval = systemConfigCache.getCacheInteger(SystemConfigKeyEnum.REFRESH_INTERVAL.getCode(),10);
        Calendar cal = Calendar.getInstance();
        cal.setTime(userDO.getGmtRefresh());
        cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE) + interval);
        Long countDown = cal.getTime().getTime() - DateTools.today().getTime();
        countDown = countDown / 1000;
        return countDown <= 0 ? 0 : countDown.intValue();
    }
    
    @Override
    public Integer getTodayShareCount(Long userId) {
        TopListQueryCondition queryCondition = new TopListQueryCondition();
        queryCondition.setUserId(userId).setGmtModifyStart(DateTools.todayStart())
        .setGmtModifyEnd(DateTools.todayEnd()).setPosition(TopListPositionEnum.分享.getCode());
        return topListDAO.getCount(queryCondition);
    }

    @Override
    public List<UserDO> getTopUsers(String openId) {
        UserDO top1 = getRandom(openId, TopListPositionEnum.充值.getCode());
        UserDO top2 = getRandom("", TopListPositionEnum.充值.getCode());
        UserDO top3 = getRandom(openId, TopListPositionEnum.分享.getCode());
        UserDO top4 = getRandom("", TopListPositionEnum.分享.getCode());
        List<UserDO> list = Lists.newArrayList(top1,top2,top3,top4);
        List<UserDO> result = Lists.newArrayList();
        for(UserDO userDO : list){
            if(!contains(result, userDO)){
                result.add(userDO);
            }
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

    @Override
    public TopListDO getValidTop(Long userId,TopListPositionEnum position) {
        return topListDAO.getValidByUserId(userId, position.getCode());
    }

    @Override
    public void randomRefresh() {
        List<UserDO> list = userDAO.getRandom();
        if(CollectionTools.isNotEmpty(list)){
            for(UserDO user : list){
                if(user != null){
                    userDAO.refresh(user.getId());
                }
            }
        }
    }

    @Override
    public void addCoins(Long userId, Integer coins) {
        userDAO.topup(userId, coins);
    }

    @Override
    public UserDO getByExtId(Long extId) {
        return userDAO.getByExtId(extId);
    }
}
