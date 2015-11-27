package com.pg.web.admin.webpage.action;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.FormField;
import com.alibaba.citrus.turbine.dataresolver.FormGroup;
import com.pg.biz.manager.TransactionManager;
import com.pg.dal.model.EmployeeDO;
import com.pg.dal.model.PurchaseDO;
import com.pg.dal.model.PurchaseItemDO;
import com.pg.web.admin.common.AuthenticationToken;
import com.pg.web.admin.model.form.PurchaseItemsFO;

public class PurchaseAction {
	@Autowired
	private HttpSession session;
	
	@Autowired
	private TransactionManager transactionManager;
	
	public void doUpdate(@FormGroup("purchase") PurchaseDO purchaseDO, 
						 @FormField(name="customerId",group="purchase") Long customerId) throws Exception{
		EmployeeDO loginedUser = AuthenticationToken.getLoginedUser(session);
		if(purchaseDO.getId() == null){
			transactionManager.createPurchase(purchaseDO,loginedUser.getId(),customerId);
		} else {
			transactionManager.updatePurchase(purchaseDO,loginedUser.getId(),customerId);
		}
	}
	
	public void doUpdateItems(@FormGroup("purchaseItem") PurchaseItemsFO purchaseItemsFO){
		List<PurchaseItemDO> list = purchaseItemsFO.getDO();
		for(PurchaseItemDO purchaseItemDO : list){
			transactionManager.updatePurchaseItem(purchaseItemDO.getId(), purchaseItemDO.getQuantity());
		}
		transactionManager.recalculate(purchaseItemsFO.getOrderId());
	}
	
	public void doDelete(@FormGroup("purchase") PurchaseDO purchaseDO,
						 @FormField(name="customerId",group="purchase") Long customerId){
		EmployeeDO loginedUser = AuthenticationToken.getLoginedUser(session);
		transactionManager.deletePurchase(purchaseDO.getId(),loginedUser.getId(),customerId);
	}
}
