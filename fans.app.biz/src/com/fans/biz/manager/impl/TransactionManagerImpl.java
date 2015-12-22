package com.fans.biz.manager.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.fans.biz.manager.TransactionManager;
import com.fans.dal.dao.CoinsDAO;
import com.fans.dal.dao.TopupDAO;
import com.fans.dal.dao.UserDAO;
import com.fans.dal.enumerate.CoinsTypeEnum;
import com.fans.dal.enumerate.PayStatusEnum;
import com.fans.dal.enumerate.TopupStatusEnum;
import com.fans.dal.model.CoinsDO;
import com.fans.dal.model.TopupDO;
import com.fans.dal.model.UserDO;
import com.victor.framework.common.tools.DateTools;

public class TransactionManagerImpl implements TransactionManager{

	@Autowired
	private TopupDAO topupDao;
	
	@Autowired
	private UserDAO userDao;
	
	@Autowired
	private CoinsDAO coinsDao;
	
	@Override
	public TopupDO createTopup(TopupDO topupDO) {
		Long id = topupDao.insert(topupDO);
		topupDO.setId(id);
		return topupDO;
	}

	@Override
	public void topupSuccess(Long topupId, String weixinOrderId) {
		TopupDO topupDO = topupDao.getById(topupId);
		if(topupDO==null){
			return;
		}
		Integer amount = topupDO.getAmount();
		Long userId = topupDO.getUserId();
		if(userId!=null && amount != null && amount > 0){
			userDao.topup(userId, amount);
			CoinsDO coinsDO = new CoinsDO();
			coinsDO.setType(CoinsTypeEnum.充值.getCode());
			coinsDO.setAmount(amount);
			coinsDO.setTopupId(topupId);
			coinsDO.setUserId(userId);
			coinsDO.setOpenId(topupDO.getOpenId());
			coinsDO.setDescription("充值获得金币");
			coinsDao.insert(coinsDO);
			updateTopup(topupId,weixinOrderId,TopupStatusEnum.成功.getCode());
		}
		//TODO 这里有异常处理的问题
	}

	@Override
	public void topupFailed(Long topupId, String weixinOrderId) {
		updateTopup(topupId,weixinOrderId,TopupStatusEnum.失败.getCode());
	}
	
	private void updateTopup(Long id, String weixinOrderId, Integer status) {
		TopupDO topupDO = new TopupDO();
		topupDO.setId(id);
		topupDO.setWeixinOrderId(weixinOrderId);
		topupDO.setStatus(status);
		topupDao.update(topupDO);
	}

	@Override
	public PayStatusEnum buyVip(Long userId, Integer coins, Integer month) {
		try {
			if(month <= 0){
				return PayStatusEnum.支付失败;
			}
			PayStatusEnum payStatus = pay(userId, coins);
			if(payStatus.getSuccess()){
				Date expire = DateTools.getDayBegin(DateTools.today());
				DateTools.addMonth(expire, month);
				userDao.vipExtend(userId, expire);
				return PayStatusEnum.支付成功;
			}
			return payStatus;
		} catch (Exception e) {
			e.printStackTrace();
			return PayStatusEnum.支付失败;
		}
	}

	@Override
	public PayStatusEnum pay(Long userId, Integer coins) {
		try {
			UserDO userDO = userDao.getById(userId);
			if(userDO==null){
				return PayStatusEnum.账户异常;
			}
			Integer exist = userDO.getCoins();
			if(exist == null || exist < coins){
				return PayStatusEnum.余额不足;
			}
			Integer update = exist - coins;
			userDO.setCoins(update);
			userDao.update(userDO);
			return PayStatusEnum.支付成功;
		} catch (Exception e) {
			e.printStackTrace();
			return PayStatusEnum.支付失败;
		}
	}

}
