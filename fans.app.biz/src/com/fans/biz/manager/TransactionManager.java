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
	void updateTopup(TopupDO topupDO);
	
	/**
	 * 获取充值记录
	 * @param uuid
	 * @return
	 */
	TopupDO getTopup(String uuid);
	TopupDO getTopup(Long id);
	
	/**
	 * 交易成功
	 * @param topupUUId
	 * @param weixinOrderId
	 */
	void paySuccess(String topupUUId, String weixinOrderId);
	
	/**
	 * 交易失败
	 * @param topupId
	 * @param weixinOrderId
	 */
	void payFailed(String topupUUId, String weixinOrderId);
	
	/**
	 * 充值成功
	 * @param id
	 * @param weixinOrderId
	 * @param status
	 * @return
	 */
	void payTopup(String topupUUId, String weixinOrderId);
	
	/**
	 * 购买VIP
	 * @param userId
	 * @param coins
	 * @param month
	 */
	void payVip(String topupUUId, String weixinOrderId);
	
	/**
	 * 购买超级置顶
	 * @param userId
	 * @param coins
	 * @param minute
	 * @return
	 */
	void payZhuangB(String topupUUId, String weixinOrderId);
	
	
	/**
	 * 用金币购买
	 * @param userId
	 * @param data1
	 * @param type
	 * @return
	 */
	PayStatusEnum buy(Long userId, Integer data1, Integer type);
	/**
	 * 购买VIP
	 * @param userId
	 * @param coins
	 * @param month
	 */
	PayStatusEnum buyVip(Long userId, Integer month);
	
	/**
	 * 购买超级置顶
	 * @param userId
	 * @param coins
	 * @param minute
	 * @return
	 */
	PayStatusEnum buyZhuangB(Long userId, Integer minutes);
}
