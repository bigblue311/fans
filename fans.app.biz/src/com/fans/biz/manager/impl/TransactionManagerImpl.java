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
	public void updateTopup(TopupDO topupDO) {
		topupDao.update(topupDO);
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
    	if(topupType.getCode().intValue() == TopupTypeEnum.充值.getCode().intValue()){
    		payTopup(topupUUId,weixinOrderId);
    		return;
    	}
    	if(topupType.getCode().intValue() == TopupTypeEnum.购买VIP.getCode().intValue()){
    		payVip(topupUUId,weixinOrderId);
    		return;
    	}
    	if(topupType.getCode().intValue() == TopupTypeEnum.购买置顶.getCode().intValue()){
    		payZhuangB(topupUUId,weixinOrderId);
    		return;
    	}
	}

	@Override
	public void payTopup(String topupUUId, String weixinOrderId) {
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
	public void payVip(String topupUUId, String weixinOrderId) {
		TopupDO topupDO = topupDao.getByUUId(topupUUId);
		if(topupDO==null){
			return;
		}
		Long userId = topupDO.getUserId();
		Integer month = Integer.parseInt(topupDO.getData1());
		if(month==null || month <= 0 || userId == null){
			updateTopup(topupDO.getId(),weixinOrderId,TopupStatusEnum.业务异常.getCode());
			return;
		}
		UserDO user = userDao.getById(userId);
		Date expire = DateTools.getDayBegin(DateTools.today());
		//如果已经是VIP, 那么再这个时间上续
		if(user!=null && user.getGmtVipExpire()!=null && user.getGmtVipExpire().after(expire)){
			expire = user.getGmtVipExpire();
		}
		Date vipExpire = DateTools.addMonth(expire, month);
		userDao.vipExtend(userId, vipExpire);
		updateTopup(topupDO.getId(),weixinOrderId,TopupStatusEnum.成功.getCode());
	}
	
	@Override
	public void payZhuangB(String topupUUId, String weixinOrderId) {
		TopupDO topupDO = topupDao.getByUUId(topupUUId);
		if(topupDO==null){
			return;
		}
		Long userId = topupDO.getUserId();
		Integer minutes = Integer.parseInt(topupDO.getData1());
		if(minutes == null || minutes <= 0 || userId == null){
			updateTopup(topupDO.getId(),weixinOrderId,TopupStatusEnum.业务异常.getCode());
			return;
		}
		Date gmtReserve = DateTools.addMinute(DateTools.today(), minutes);
		userDao.startZhuangB(userId, gmtReserve);
		updateTopup(topupDO.getId(),weixinOrderId,TopupStatusEnum.成功.getCode());
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
	public PayStatusEnum buy(Long userId, Integer data1, Integer type) {
		TopupTypeEnum topupType = TopupTypeEnum.getByCode(type);
    	if(topupType == null){
    		return PayStatusEnum.支付失败;
    	}
    	if(topupType.getCode().intValue() == TopupTypeEnum.购买VIP.getCode().intValue()){
    		return buyVip(userId,data1);
    	}
    	if(topupType.getCode().intValue() == TopupTypeEnum.购买置顶.getCode().intValue()){
    		return buyZhuangB(userId,data1);
    	}
    	return PayStatusEnum.支付失败;
	}

	@Override
	public PayStatusEnum buyVip(Long userId, Integer month) {
		try {
			if(month==null || month <= 0){
				return PayStatusEnum.支付失败;
			}
			Integer coins = priceManager.buyVipUseCoins(month);
			PayStatusEnum payStatus = useCoins(userId, coins);
			if(payStatus.getSuccess()){
				UserDO user = userDao.getById(userId);
				Date expire = DateTools.getDayBegin(DateTools.today());
				//如果已经是VIP, 那么再这个时间上续
				if(user!=null && user.getGmtVipExpire()!=null && user.getGmtVipExpire().after(expire)){
					expire = user.getGmtVipExpire();
				}
				Date vipExpire = DateTools.addMonth(expire, month);
				userDao.vipExtend(userId, vipExpire);
				return PayStatusEnum.支付成功;
			}
			return payStatus;
		} catch (Exception e) {
			e.printStackTrace();
			return PayStatusEnum.支付失败;
		}
	}
	
	@Override
	public PayStatusEnum buyZhuangB(Long userId, Integer minutes) {
		try {
			if(minutes == null || minutes <= 0){
				return PayStatusEnum.支付失败;
			}
			Integer coins = priceManager.buyZhuangBUseCoins(minutes);
			PayStatusEnum payStatus = useCoins(userId, coins);
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
	
	private PayStatusEnum useCoins(Long userId, Integer coins) {
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
