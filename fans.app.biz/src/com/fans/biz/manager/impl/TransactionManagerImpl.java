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
import com.fans.dal.enumerate.TopupTypeEnum;
import com.fans.dal.model.CoinsDO;
import com.fans.dal.model.TopupDO;
import com.fans.dal.model.UserDO;
import com.victor.framework.common.tools.DateTools;
import com.victor.framework.common.tools.UUIDTools;

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
		topupDO.setUuid(UUIDTools.getID());
		Long id = topupDao.insert(topupDO);
		topupDO.setId(id);
		return topupDO;
	}
	
	@Override
	public TopupDO getTopup(String uuid) {
		return topupDao.getByUUId(uuid);
	}

	@Override
	public TopupDO getTopup(Long id) {
		return topupDao.getById(id);
	}
	
	@Override
	public void paySuccess(String topupUUId, String weixinOrderId) {
		TopupDO topupDO = topupDao.getByUUId(topupUUId);
		if(topupDO==null || topupDO.getType() == null){
			return;
		}
		Integer type = topupDO.getType();
		TopupTypeEnum topupType = TopupTypeEnum.getByCode(type);
    	if(topupType == null){
    		return;
    	}
    	if(topupType.getCode() == TopupTypeEnum.充值.getCode()){
    		topupSuccess(topupUUId,weixinOrderId);
    	}
    	if(topupType.getCode() == TopupTypeEnum.购买VIP.getCode()){
    		buyVipSuccess(topupUUId,weixinOrderId);
    	}
    	if(topupType.getCode() == TopupTypeEnum.充值.getCode()){
    		buyZhuangBSuccess(topupUUId,weixinOrderId);
    	}
	}

	@Override
	public void topupSuccess(String topupUUId, String weixinOrderId) {
		TopupDO topupDO = topupDao.getByUUId(topupUUId);
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
			coinsDO.setTopupId(topupDO.getId());
			coinsDO.setUserId(userId);
			coinsDO.setOpenId(topupDO.getOpenId());
			coinsDO.setDescription("充值获得金币");
			coinsDao.insert(coinsDO);
			updateTopup(topupDO.getId(),weixinOrderId,TopupStatusEnum.成功.getCode());
			return;
		} else {
			updateTopup(topupDO.getId(),weixinOrderId,TopupStatusEnum.业务异常.getCode());
			return;
		}
	}

	@Override
	public void payFailed(String topupUUId, String weixinOrderId) {
		TopupDO topupDO = topupDao.getByUUId(topupUUId);
		if(topupDO!=null){
			updateTopup(topupDO.getId(),weixinOrderId,TopupStatusEnum.支付失败.getCode());
		}
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
			PayStatusEnum payStatus = payCoins(userId, coins);
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
	public void buyVipSuccess(String topupUUId, String weixinOrderId) {
		TopupDO topupDO = topupDao.getByUUId(topupUUId);
		if(topupDO==null){
			return;
		}
		Long userId = topupDO.getUserId();
		Integer month = Integer.parseInt(topupDO.getData1());
		if(userId!=null){
			Date expire = DateTools.getDayBegin(DateTools.today());
			DateTools.addMonth(expire, month);
			userDao.vipExtend(userId, expire);
			updateTopup(topupDO.getId(),weixinOrderId,TopupStatusEnum.成功.getCode());
			return;
		} else {
			updateTopup(topupDO.getId(),weixinOrderId,TopupStatusEnum.业务异常.getCode());
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
			PayStatusEnum payStatus = payCoins(userId, coins);
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
	public void buyZhuangBSuccess(String topupUUId, String weixinOrderId) {
		TopupDO topupDO = topupDao.getByUUId(topupUUId);
		if(topupDO==null){
			return;
		}
		Long userId = topupDO.getUserId();
		Integer minutes = Integer.parseInt(topupDO.getData1());
		if(userId!=null){
			Date gmtReserve = DateTools.addMinute(DateTools.today(), minutes);
			userDao.startZhuangB(userId, gmtReserve);
			updateTopup(topupDO.getId(),weixinOrderId,TopupStatusEnum.成功.getCode());
			return;
		} else {
			updateTopup(topupDO.getId(),weixinOrderId,TopupStatusEnum.业务异常.getCode());
			return;
		}
	}

	@Override
	public PayStatusEnum payCoins(Long userId, Integer coins) {
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
