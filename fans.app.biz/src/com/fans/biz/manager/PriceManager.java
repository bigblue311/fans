package com.fans.biz.manager;

/**
 * 定价中心
 * @author victorhan
 *
 */
public interface PriceManager {
	
	/**
	 * 充值得金币
	 * @param money
	 * @return
	 */
	Integer topupM2C(Integer money);
	
	/**
	 * 购买VIP
	 * @param month
	 * @return
	 */
	Integer buyVipUseCoins(Integer month); //用金币
	Integer buyVipUseMoney(Integer month); //用现金
	
	/**
	 * 购买超级置顶价格
	 * @param minute
	 * @return
	 */
	Integer buyZhuangBUseCoins(Integer minute);	//用金币
	Integer buyZhuangBUseMoney(Integer minute);	//用现金
}
