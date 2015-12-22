package com.fans.biz.manager.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.fans.biz.manager.PriceManager;
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
	
	@Autowired
	private PriceManager priceManager;
	
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
		Integer money = topupDO.getAmount();
		Integer coins = priceManager.topupM2C(money);
		Long userId = topupDO.getUserId();
		if(userId!=null && coins != null && coins > 0){
			userDao.topup(userId, coins);
			CoinsDO coinsDO = new CoinsDO();
			coinsDO.setType(CoinsTypeEnum.充值.getCode());
			coinsDO.setAmount(coins);
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
	public PayStatusEnum buyVip(Long userId, Integer month) {
		try {
			if(month==null || month <= 0){
				return PayStatusEnum.支付失败;
			}
			Integer coins = priceManager.buyVipUseCoins(month);
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
	public void buyVipSuccess(Long topupId, String weixinOrderId, Integer month) {
		TopupDO topupDO = topupDao.getById(topupId);
		if(topupDO==null){
			return;
		}
		Long userId = topupDO.getUserId();
		if(userId!=null){
			Date expire = DateTools.getDayBegin(DateTools.today());
			DateTools.addMonth(expire, month);
			userDao.vipExtend(userId, expire);
			updateTopup(topupId,weixinOrderId,TopupStatusEnum.成功.getCode());
			return;
		}
	}
	
	@Override
	public PayStatusEnum buyZhuangB(Long userId, Integer minutes) {
		try {
			if(minutes == null || minutes < 0){
				return PayStatusEnum.支付失败;
			}
			Integer coins = priceManager.buyZhuangBUseCoins(minutes);
			PayStatusEnum payStatus = pay(userId, coins);
			if(payStatus.getSuccess()){
				Date gmtReserve = DateTools.addMinute(DateTools.today(), minutes);
				userDao.startZhuangB(userId, gmtReserve);
				return PayStatusEnum.支付成功;
			}
			return payStatus;
		} catch (Exception e) {
			e.printStackTrace();
			return PayStatusEnum.支付失败;
		}
	}
	
	@Override
	public void buyZhuangBSuccess(Long topupId, String weixinOrderId, Integer minutes) {
		TopupDO topupDO = topupDao.getById(topupId);
		if(topupDO==null){
			return;
		}
		Long userId = topupDO.getUserId();
		if(userId!=null){
			Date gmtReserve = DateTools.addMinute(DateTools.today(), minutes);
			userDao.startZhuangB(userId, gmtReserve);
			updateTopup(topupId,weixinOrderId,TopupStatusEnum.成功.getCode());
			return;
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
