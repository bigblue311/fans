package com.fans.biz.manager;

import com.fans.dal.enumerate.PayStatusEnum;
import com.fans.dal.model.TopupDO;

/**
 * 交易管理
 * @author victorhan
 *
 */
public interface TransactionManager {
	
	/**
	 * 创建一笔充值记录
	 * @param topupDO
	 * @return
	 */
	TopupDO createTopup(TopupDO topupDO);
	
	/**
	 * 充值成功
	 * @param id
	 * @param weixinOrderId
	 * @param status
	 * @return
	 */
	void topupSuccess(String topupUUId, String weixinOrderId);
	
	/**
	 * 充值失败
	 * @param topupId
	 * @param weixinOrderId
	 */
	void topupFailed(String topupUUId, String weixinOrderId);
	
	/**
	 * 购买VIP
	 * @param userId
	 * @param coins
	 * @param month
	 */
	PayStatusEnum buyVip(Long userId, Integer month);
	void buyVipSuccess(String topupUUId, String weixinOrderId);
	
	/**
	 * 购买超级置顶
	 * @param userId
	 * @param coins
	 * @param minute
	 * @return
	 */
	PayStatusEnum buyZhuangB(Long userId, Integer minutes);
	void buyZhuangBSuccess(String topupUUId, String weixinOrderId);
	
	/**
	 * 支付金币
	 * @param userId
	 * @param coins
	 */
	PayStatusEnum pay(Long userId, Integer coins);
}
