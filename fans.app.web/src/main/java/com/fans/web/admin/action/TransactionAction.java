package com.fans.web.admin.action;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.FormGroup;
import com.fans.admin.common.AuthenticationToken;
import com.fans.admin.model.form.TopupFO;
import com.fans.biz.manager.TransactionManager;
import com.fans.biz.manager.UserManager;
import com.fans.dal.enumerate.TopupStatusEnum;
import com.fans.dal.enumerate.TopupTypeEnum;
import com.fans.dal.model.EmployeeDO;
import com.fans.dal.model.TopupDO;
import com.fans.dal.model.UserDO;
import com.victor.framework.common.tools.StringTools;

public class TransactionAction {
    
    @Autowired
    private TransactionManager transactionManager;
    
    @Autowired
    private UserManager userManager;
    
    @Autowired
    private HttpSession session;
    
    public void doTopup(@FormGroup("topup") TopupFO topupFO){
        EmployeeDO loginUser = AuthenticationToken.getLoginedUser(session);
        Long userId = topupFO.getUserId();
        Integer cash = topupFO.getCash();
        UserDO userDO = userManager.getById(userId);
        if(userDO == null){
            return;
        }
        if(cash == null || cash <= 0){
            return;
        }
        TopupDO topupDO = new TopupDO();
        topupDO.setUserId(userDO.getId());
        topupDO.setOpenId(userDO.getOpenId());
        topupDO.setAmount(cash);
        topupDO.setStatus(TopupStatusEnum.待支付.getCode());
        topupDO.setType(TopupTypeEnum.积分充值.getCode());
        topupDO.setData4(loginUser.getName());
        if(StringTools.isEmpty(topupFO.getDescription())){
            topupDO.setDescription(TopupTypeEnum.积分充值.getDesc());
        } else {
            topupDO.setDescription(topupFO.getDescription());
        }
        transactionManager.createTopup(topupDO);
        
        transactionManager.paySuccess(topupDO.getUuid(), "后台充值");
    }
    
    public void doRollback(@FormGroup("topup") TopupFO topupFO){
        EmployeeDO loginUser = AuthenticationToken.getLoginedUser(session);
        Long topupId = topupFO.getTopupId();
        transactionManager.payTopupRollback(topupId, loginUser.getName());
    }
}
