package com.fans.biz.manager.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.fans.biz.manager.UserManager;
import com.fans.dal.dao.UserDAO;
import com.fans.dal.model.UserDO;
import com.fans.dal.query.UserQueryCondition;
import com.victor.framework.common.tools.StringTools;
import com.victor.framework.dal.basic.Paging;

public class UserManagerImpl implements UserManager{

    @Autowired
    private UserDAO userDAO;
    
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
        if(userDO.canRefresh()){
            userDAO.refresh(id);
        }
    }

    @Override
    public void topup(Long id, Date expire) {
        UserDO userDO = new UserDO();
        userDO.setId(id);
        userDO.setGmtVipExpire(expire);
        userDAO.topup(userDO);
    }

    @Override
    public UserDO getById(Long id) {
        return userDAO.getById(id);
    }

    @Override
    public UserDO getByOpenId(String openId) {
        return userDAO.getByOpenId(openId);
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
    
}
