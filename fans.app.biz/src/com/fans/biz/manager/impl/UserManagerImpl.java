package com.fans.biz.manager.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.fans.biz.manager.UserManager;
import com.fans.dal.cache.SystemConfigCache;
import com.fans.dal.dao.QrcodeDAO;
import com.fans.dal.dao.QrcodeScanDAO;
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
import com.victor.framework.common.tools.MD5;
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
    
    @Autowired
    private QrcodeDAO qrcodeDAO;
    
    @Autowired
    private QrcodeScanDAO qrcodeScanDAO;
    
    @Override
    public void create(UserDO userDO) {
        boolean find = false;
        String openId = userDO.getOpenId();
        Long extId = userDO.getExtId();
        Long skvId = userDO.getSkvId();
        if(StringTools.isNotEmpty(openId)){
            if(userDAO.getByOpenId(openId) != null) {
                find = true;
            }
        } 
        if(extId!=null){
            if(userDAO.getByExtId(extId) != null){
                find = true;
            }
        }
        if(skvId!=null){
            if(userDAO.getBySkvId(skvId) != null){
                find = true;
            }
        }
        if(!find){
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
    public Integer getTodayFriendCount(Long userId) {
        TopListQueryCondition queryCondition = new TopListQueryCondition();
        queryCondition.setUserId(userId).setGmtModifyStart(DateTools.todayStart())
        .setGmtModifyEnd(DateTools.todayEnd()).setPosition(TopListPositionEnum.加好友.getCode());
        return topListDAO.getCount(queryCondition);
    }
    
    @Override
    public String getTodayTask(UserDO userDO) {
        if(userDO==null){
            return "账号异常,请联系客服";
        }
        
        Integer shareMax = systemConfigCache.getCacheInteger(SystemConfigKeyEnum.SHARE_MAX.getCode(), 3);
        Integer shareCoins = systemConfigCache.getCacheInteger(SystemConfigKeyEnum.SHARE_COINS.getCode(), 50);
        Integer shareCount = getTodayShareCount(userDO.getId());

        Integer shareLeft = shareMax - shareCount - 1;
        shareLeft = shareLeft<=0?0:shareLeft;
        
        Integer friendMax = systemConfigCache.getCacheInteger(SystemConfigKeyEnum.FRIEND_MAX.getCode(), 6);
        Integer friendCoins = systemConfigCache.getCacheInteger(SystemConfigKeyEnum.FRIEND_COINS.getCode(), 10);
        Integer friendCount = getTodayFriendCount(userDO.getId());
        
        Integer friendLeft = friendMax - friendCount;
        friendLeft = friendLeft<=0?0:friendLeft;
        
        Integer taskCount = shareLeft + friendLeft;
        Integer taskCoins = (shareLeft*shareCoins) + (friendLeft*friendCoins);
        
        if(taskCount!=null && taskCount.intValue()>0){
            return "您今日再分享"+shareLeft+"次和加好友"+friendLeft+"*5个, 还可以获得"+taskCoins+"个金币";
        } else {
            return null;
        }
        
    }

    @Override
    public List<UserDO> getTopUsers() {
        TopListQueryCondition queryCondition = new TopListQueryCondition();
        queryCondition.setValid(0).setPosition(TopListPositionEnum.充值.getCode());
        List<TopListDO> list = topListDAO.getByCondition(queryCondition);
        if(CollectionTools.isEmpty(list)){
            return Lists.newArrayList();
        }
        List<UserDO> result = Lists.newArrayList();
        for(TopListDO topListDO : list){
            UserDO userDO = getVipUserDO(topListDO);
            if(userDO!=null){
                result.add(userDO);
            }
        }
        if(CollectionTools.isNotEmpty(result)){
            Collections.reverse(result);
        }
        return result;
    }
    
    private UserDO getVipUserDO(TopListDO topListDO){
        if(topListDO != null && topListDO.getUserId() != null){
            UserDO userDO = userDAO.getById(topListDO.getUserId());
            if(userDO != null && 
               StringTools.isNotEmpty(userDO.getQrcode()) && 
               StringTools.isNotEmpty(userDO.getHeadImg()) && 
               topListDO.getGmtEnd() != null){
                Calendar cal = Calendar.getInstance();
                cal.setTime(topListDO.getGmtEnd());
                Long countDown = topListDO.getGmtEnd().getTime() - DateTools.today().getTime();
                countDown = countDown / 1000;
                userDO.setVipCountDown(countDown <= 0 ? 0 : countDown.intValue());
                return userDO;
            }
        }
        return null;
    }

    @Override
    public TopListDO getValidTop(Long userId,TopListPositionEnum position) {
        return topListDAO.getValidByUserId(userId, position.getCode());
    }

    @Override
    public void randomRefresh() {
        List<UserDO> list = userDAO.getRandom();
        if(CollectionTools.isEmpty(list)){
            return;
        }
        for(UserDO user : list){
            if(user == null){
                continue;
            }
            userDAO.refresh(user.getId());
        }
    }

    @Override
    public void addCoins(Long userId, Integer coins) {
        userDAO.topup(userId, coins);
    }
    
    @Override
    public void click(Long userId) {
        userDAO.click(userId);
    }

    @Override
    public UserDO getByExtId(Long extId) {
        return userDAO.getByExtId(extId);
    }

    @Override
    public UserDO getBySkvId(Long skvId) {
        return userDAO.getBySkvId(skvId);
    }

    @Override
    public SkvUserDO getSkvUserByPassword(String phone, String password) {
        password = MD5.getMD5(password);
        return skvUserDAO.getByPassword(phone, password);
    }

    @Override
    public UserDO merge(Long userID, Long skvId) {
        UserDO weixinUser = userDAO.getById(userID);
        UserDO skvUser = userDAO.getBySkvId(skvId);
        if(weixinUser==null){
            //不是从微信进入的用户, 不合并
            return null;
        }
        if(weixinUser!=null && skvUser == null){
            //绑定SKVID
            if(weixinUser.getSkvId() == null){
                weixinUser.setSkvId(skvId);
                update(weixinUser);
            }
            return weixinUser;
        }
        if(weixinUser.getId().longValue() == skvUser.getId().longValue()){
            //已经合并过了
            return weixinUser;
        }
        //假如是两个账号,那么合并
        Integer coins = skvUser.getCoins();
        if(coins!=null && coins>50){
            Integer coinsToAdd = coins - 50;
            addCoins(userID,coinsToAdd);
        }
        weixinUser = userMerge(weixinUser, skvUser);
        //直接删了
        userDAO.delete(skvUser.getId());
        update(weixinUser);
        
        qrcodeDAO.updateByOpenId(weixinUser.getOpenId(), skvId);
        qrcodeScanDAO.updateByOpenId(weixinUser.getOpenId(), skvId);
        return userDAO.getById(userID);
    }
    
    private UserDO userMerge(UserDO user1, UserDO user2){
        UserDO merged = user1;
        Field[] fields = user1.getClass().getDeclaredFields();
        for(Field field : fields){
            String getMethod = "get"+field.getName();
            String setMethod = "set"+field.getName();
            Object obj1 = doGetMethod(user1,getMethod);
            Object obj2 = doGetMethod(user2,getMethod);
            if(obj1 == null && obj2 != null){
                doSetMethod(merged,setMethod,obj2);
            }
        }
        return merged;
    }
    
    private Object doGetMethod(UserDO userDO, String methodName){
        Method[] methods = userDO.getClass().getDeclaredMethods();
        for(Method method : methods){
            if(!method.getName().equalsIgnoreCase(methodName)){
                continue;
            }
            try {
                return method.invoke(userDO, new Object[0]);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }
    
    private void doSetMethod(UserDO userDO, String methodName, Object value){
        Method[] methods = userDO.getClass().getDeclaredMethods();
        for(Method method : methods){
            if(!method.getName().equalsIgnoreCase(methodName)){
                continue;
            }
            try {
                method.invoke(userDO, value);
            } catch (Exception e) {
                return;
            }
        }
    }
}
