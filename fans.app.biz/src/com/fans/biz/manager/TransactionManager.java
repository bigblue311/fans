package com.fans.biz.manager;

import com.fans.dal.enumerate.PayStatusEnum;
import com.fans.dal.model.TopupDO;

public interface TransactionManager {
	
	/**
	 * 创建一笔充值记录
	 * @param topupDO
	 * @return
	 */
	TopupDO createTopup(TopupDO topupDO);
	
	/**
	 * 更新一条充值记录
	 * @param id
	 * @param weixinOrderId
	 * @param status
	 * @return
	 */
	void topupSuccess(Long topupId, String weixinOrderId);
	void topupFailed(Long topupId, String weixinOrderId);
	
	/**
	 * 购买VIP
	 * @param userId
	 * @param coins
	 * @param month
	 */
	PayStatusEnum buyVip(Long userId, Integer coins, Integer month);
	
	/**
	 * 
	 * @param userId
	 * @param coins
	 */
	PayStatusEnum pay(Long userId, Integer coins);
}
