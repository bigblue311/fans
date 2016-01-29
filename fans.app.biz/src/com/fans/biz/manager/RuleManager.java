package com.fans.biz.manager;

import com.fans.biz.model.HelpLinkResult;
import com.fans.dal.model.UserDO;

public interface RuleManager {
    
    /**
     * 普通置顶规则
     * @return
     */
    HelpLinkResult checkTop(UserDO userDO); 
    
    /**
     * 固定规则
     * @param coins
     * @return
     */
    HelpLinkResult checkSuperTopCoins(UserDO userDO,int coins);
    HelpLinkResult checkSuperTopMoney(UserDO userDO);
}
