package com.fans.biz.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.fans.biz.manager.RuleManager;
import com.fans.biz.manager.TransactionManager;
import com.fans.biz.manager.UserManager;
import com.fans.biz.model.HelpLinkResult;
import com.fans.dal.cache.SystemConfigCache;
import com.fans.dal.enumerate.SystemConfigKeyEnum;
import com.fans.dal.model.UserDO;
import com.victor.framework.common.tools.StringTools;

public class RuleManagerImpl implements RuleManager{

    @Autowired
    private TransactionManager transactionManager;
    
    @Autowired
    private UserManager userManager;
    
    @Autowired
    private SystemConfigCache systemConfigCache;
    
    @Override
    public HelpLinkResult checkTop(UserDO userDO) {
        //用户
        HelpLinkResult result = checkUserExist(userDO);
        if(!result.isSuccess()) return result;
        //用户资料
        result = checkUserInfo(userDO);
        if(!result.isSuccess()) return result;
        //vip
        result = checkVip(userDO);
        if(!result.isSuccess()) return result;
        //冷却时间
        result = checkReresh(userDO);
        if(!result.isSuccess()) return result;
        //好友
        result = checkFriend(userDO);
        if(!result.isSuccess()) return result;
        return result;
    }

    @Override
    public HelpLinkResult checkSuperTopCoins(UserDO userDO,int coins) {
        //用户
        HelpLinkResult result = checkUserExist(userDO);
        if(!result.isSuccess()) return result;
        //用户资料
        result = checkUserInfo(userDO);
        if(!result.isSuccess()) return result;
        //vip
        result = checkVip(userDO);
        if(!result.isSuccess()) return result;
        //金币
        result = checkCoins(userDO,coins);
        if(!result.isSuccess()) return result;
        return result;
    }
    
    @Override
    public HelpLinkResult checkSuperTopMoney(UserDO userDO) {
        //用户
        HelpLinkResult result = checkUserExist(userDO);
        if(!result.isSuccess()) return result;
        //用户资料
        result = checkUserInfo(userDO);
        if(!result.isSuccess()) return result;
        //vip
        result = checkVip(userDO);
        if(!result.isSuccess()) return result;
        return result;
    }
    
    private HelpLinkResult checkUserExist(UserDO userDO){
        HelpLinkResult result = new HelpLinkResult();
        
        if(userDO == null){
            result.setLink("/vip.htm?open=topupDetail#service1");
            result.setTitle("联系客服");
            result.setMessage("账号异常,请联系客服");
            result.setSuccess(false);
            return result;
        }
        result.setSuccess(true);
        return result;
    }
    
    private HelpLinkResult checkVip(UserDO userDO){
        HelpLinkResult result = new HelpLinkResult();
        
        if(userDO.isVip()){
            result.setSuccess(true);
            return result;
        } else {
            Integer shareCount = userManager.getTodayShareCount(userDO.getId());
            if(shareCount != null && shareCount == 0){
                result.setLink("/share.htm");
                result.setTitle("我要分享");
                result.setMessage("您的爆粉激活到期了,每天第一次分享可激活24小时");
                result.setSuccess(false);
                return result;
            } else {
                result.setLink("/vip.htm?open=vipDetail#service1");
                result.setTitle("激活我的爆粉");
                result.setMessage("您的爆粉激活到期了");
                result.setSuccess(false);
                return result;
            }
        }
    }
    
    private HelpLinkResult checkUserInfo(UserDO userDO){
        HelpLinkResult result = new HelpLinkResult();
        result.setLink("/my.htm");
        result.setTitle("完善个人资料");
        result.setMessage("请先上传个人二维码和个人头像");
        result.setSuccess(false);
        if(StringTools.isNotEmpty(userDO.getHeadImg()) && StringTools.isNotEmpty(userDO.getQrcode())){
            result.setSuccess(true);
            return result;
        }
        if(StringTools.isEmpty(userDO.getHeadImg()) && StringTools.isNotEmpty(userDO.getQrcode())){
            result.setMessage("请先上传个人头像");
            return result;
        }
        if(StringTools.isNotEmpty(userDO.getHeadImg()) && StringTools.isEmpty(userDO.getQrcode())){
            result.setMessage("请先上传个人二维码");
            return result;
        }
        if(StringTools.isEmpty(userDO.getHeadImg()) && StringTools.isEmpty(userDO.getQrcode())){
            result.setMessage("请先上传个人二维码和个人头像");
            return result;
        }
        return result;
    }
    
    private HelpLinkResult checkReresh(UserDO userDO){
        HelpLinkResult result = new HelpLinkResult();
        
        Integer countDown = userManager.nextRefresh(userDO);
        if(countDown == 0){
            result.setSuccess(true);
            return result;
        } else {
            result.setMessage("还差"+countDown+"秒后可以置顶");
            result.setLink("/index.htm");
            result.setSuccess(false);
            result.setTitle("再等等");
            return result;
        }
    }
    
    private HelpLinkResult checkFriend(UserDO userDO){
        HelpLinkResult result = new HelpLinkResult();
        
        if(userDO.getSkvId() != null){
            result.setSuccess(true);
            return result;
        } else {
            Integer friendCount = userManager.getTodayFriendCount(userDO.getId());
            if(friendCount != null && friendCount == 0){
                result.setMessage("请先加5个好友");
                result.setLink("/index.htm");
                result.setSuccess(false);
                result.setTitle("去加好友");
                return result;
            } else {
                result.setSuccess(true);
                return result;
            }
        }
    }
    
    private HelpLinkResult checkCoins(UserDO userDO, int coins){
        HelpLinkResult result = new HelpLinkResult();
        if(userDO.getCoins().intValue() >= coins){
            result.setSuccess(true);
            return result;
        } else {
            int shareCoins = getTaskShareCoins(userDO);
            int friendCoins = getTaskFriendCoins(userDO);
            if(shareCoins > 0){
                result.setLink("/share.htm");
                result.setTitle("去分享");
                result.setMessage("分享任务还可得"+shareCoins+",快去赚金币吧");
                result.setSuccess(false);
                return result;
            } 
            if(friendCoins > 0){
                result.setLink("/index.htm");
                result.setTitle("去加好友");
                result.setMessage("好友任务还可得"+friendCoins+",快去赚金币吧");
                result.setSuccess(false);
                return result;
            }
            result.setLink("/vip.htm?open=topupDetail#service1");
            result.setTitle("去充值");
            result.setMessage("今日任务已做完, 还是去充值吧");
            result.setSuccess(false);
            return result;
        }
    }
    
    private int getTaskShareCoins(UserDO userDO){
        Integer shareMax = systemConfigCache.getCacheInteger(SystemConfigKeyEnum.SHARE_MAX.getCode(), 3);
        Integer shareCoins = systemConfigCache.getCacheInteger(SystemConfigKeyEnum.SHARE_COINS.getCode(), 50);
        Integer shareCount = userManager.getTodayShareCount(userDO.getId());

        Integer shareLeft = shareMax - shareCount - 1;
        shareLeft = shareLeft<=0?0:shareLeft;
        
        Integer taskCoins = shareLeft*shareCoins;
        return taskCoins.intValue();
    }
    
    private int getTaskFriendCoins(UserDO userDO){
        Integer friendMax = systemConfigCache.getCacheInteger(SystemConfigKeyEnum.FRIEND_MAX.getCode(), 6);
        Integer friendCoins = systemConfigCache.getCacheInteger(SystemConfigKeyEnum.FRIEND_COINS.getCode(), 10);
        Integer friendCount = userManager.getTodayFriendCount(userDO.getId());
        
        Integer friendLeft = friendMax - friendCount;
        friendLeft = friendLeft<=0?0:friendLeft;
        
        Integer taskCoins = friendLeft*friendCoins;
        return taskCoins.intValue();
    }
}
