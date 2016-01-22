package com.fans.biz.manager;

import com.fans.biz.model.OrderStatisticVO;
import com.fans.biz.model.TopupVO;
import com.fans.dal.enumerate.PayStatusEnum;
import com.fans.dal.model.TopupDO;
import com.fans.dal.model.UserDO;
import com.fans.dal.query.TopupQueryCondition;
import com.victor.framework.dal.basic.Paging;

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
	 * 充值查询
	 * @param queryCondition
	 * @return
	 */
	Paging<TopupDO> getPage(TopupQueryCondition queryCondition);
	Paging<TopupVO> getVOPage(TopupQueryCondition queryCondition);
	
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
	 * 充值回滚
	 * @param topupId
	 * @param operator 处理人
	 */
	void payTopupRollback(Long topupId,String operator);
	
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
	 * @param month
	 */
	PayStatusEnum buyVip(Long userId, Integer date);
	
	/**
	 * 购买超级置顶
	 * @param userId
	 * @param coins
	 * @param minute
	 * @return
	 */
	PayStatusEnum buyZhuangB(Long userId, Integer minutes);
	
	/**
	 * 获取统计数据
	 * @return
	 */
	OrderStatisticVO getOrderStatisticVO();
	
	/**
	 * 分享和加好友
	 * @param userId
	 */
	void share(UserDO userDO);
	void friend(UserDO userDO);
}
