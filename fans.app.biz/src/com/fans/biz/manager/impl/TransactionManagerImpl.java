package com.fans.biz.manager.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.fans.biz.manager.PriceManager;
import com.fans.biz.manager.TransactionManager;
import com.fans.biz.model.OrderAlertVO;
import com.fans.biz.model.OrderStatisticVO;
import com.fans.biz.model.PriceSetVO;
import com.fans.biz.model.TopupVO;
import com.fans.dal.dao.CoinsDAO;
import com.fans.dal.dao.TopListDAO;
import com.fans.dal.dao.TopupDAO;
import com.fans.dal.dao.UserDAO;
import com.fans.dal.enumerate.CoinsTypeEnum;
import com.fans.dal.enumerate.PayStatusEnum;
import com.fans.dal.enumerate.ShoppingLevelEnum;
import com.fans.dal.enumerate.TopListPositionEnum;
import com.fans.dal.enumerate.TopupStatusEnum;
import com.fans.dal.enumerate.TopupTypeEnum;
import com.fans.dal.model.CoinsDO;
import com.fans.dal.model.TopListDO;
import com.fans.dal.model.TopupDO;
import com.fans.dal.model.UserDO;
import com.fans.dal.query.TopListQueryCondition;
import com.fans.dal.query.TopupQueryCondition;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.victor.framework.common.tools.DateTools;
import com.victor.framework.common.tools.StringTools;
import com.victor.framework.common.tools.UUIDTools;
import com.victor.framework.dal.basic.Paging;

public class TransactionManagerImpl implements TransactionManager{

	@Autowired
	private TopupDAO topupDao;
	
	@Autowired
	private UserDAO userDao;
	
	@Autowired
	private CoinsDAO coinsDao;
	
	@Autowired
	private TopListDAO topListDao;
	
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
    public Paging<TopupDO> getPage(TopupQueryCondition queryCondition) {
	    int totalSize = topupDao.getCount(queryCondition);
        @SuppressWarnings("unchecked")
        Paging<TopupDO> page = queryCondition.getPaging(totalSize, 5);
        List<TopupDO> list = topupDao.getPage(queryCondition);
        page.setData(list);
        return page;
    }
	
	@Override
    public Paging<TopupVO> getVOPage(TopupQueryCondition queryCondition) {
	    int totalSize = topupDao.getCount(queryCondition);
        @SuppressWarnings("unchecked")
        Paging<TopupVO> page = queryCondition.getPaging(totalSize, 5);
        List<TopupDO> list = topupDao.getPage(queryCondition);
        List<TopupVO> voList = Lists.transform(list, new Function<TopupDO,TopupVO>(){

            @Override
            public TopupVO apply(TopupDO topupDO) {
                return topupDO2VO(topupDO);
            }
            
        });
        page.setData(voList);
        return page;
    }
	
	private TopupVO topupDO2VO(TopupDO topupDO){
	    if(topupDO == null){
	        return null;
	    }
	    TopupVO topupVO = new TopupVO();
	    topupVO.setTopupDO(topupDO);
	    Long userId = topupDO.getUserId();
	    UserDO userDO = userDao.getById(userId);
	    if(userDO != null){
	        topupVO.setUserDO(userDO);
	    }
	    if(StringTools.isNotEmpty(topupDO.getOperator())){
	        topupVO.setOperator(topupDO.getOperator());
	    }
	    Integer type = topupDO.getType();
        TopupTypeEnum topupType = TopupTypeEnum.getByCode(type);
        if(topupType != null){
            if(topupType.getCode().intValue() == TopupTypeEnum.购买会员.getCode().intValue()){
                String product = "购买会员"+topupDO.getData1() + "天";
                topupVO.setProduct(product);
            }
            if(topupType.getCode().intValue() == TopupTypeEnum.购买置顶.getCode().intValue()){
                String product = "购买置顶"+topupDO.getData1() + "分钟";
                topupVO.setProduct(product);
            }
        }
        return topupVO;
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
    	if(topupType.getCode().intValue() == TopupTypeEnum.金币充值.getCode().intValue()){
    		payTopup(topupUUId,weixinOrderId);
    		return;
    	}
    	if(topupType.getCode().intValue() == TopupTypeEnum.购买会员.getCode().intValue()){
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
			updateTopup(topupDO.getId(),weixinOrderId,TopupStatusEnum.支付成功.getCode());
			return;
		} else {
			updateTopup(topupDO.getId(),weixinOrderId,TopupStatusEnum.业务异常.getCode());
			return;
		}
	}
	
	@Override
    public void payTopupRollback(Long topupId, String operator) {
	    TopupDO topupDO = topupDao.getById(topupId);
	    if(topupDO==null){
            return;
        }
	    Integer money = topupDO.getAmount();
	    if(money == null || money == 0){
            return;
        }
	    
        Integer coins = priceManager.topupM2C(money);
        if(coins == null || coins == 0){
            return;
        }
        
        Long userId = topupDO.getUserId();
        UserDO userDO = userDao.getById(userId);
        if(userDO != null){
            Integer existCoins = userDO.getCoins();
            Integer updateCoins = existCoins - coins > 0 ? existCoins - coins:0;
            userDO.setCoins(updateCoins);
            userDao.update(userDO);
            
            topupDO.setStatus(TopupStatusEnum.交易取消.getCode());
            topupDO.setOperator(operator);
            topupDao.update(topupDO);
        }
    }
	
	@Override
	public void payVip(String topupUUId, String weixinOrderId) {
		TopupDO topupDO = topupDao.getByUUId(topupUUId);
		if(topupDO==null){
			return;
		}
		Long userId = topupDO.getUserId();
		Integer date = Integer.parseInt(topupDO.getData1());
		if(date==null || date <= 0 || userId == null){
			updateTopup(topupDO.getId(),weixinOrderId,TopupStatusEnum.业务异常.getCode());
			return;
		}
		UserDO user = userDao.getById(userId);
		Date expire = DateTools.todayStart();
		//如果已经是VIP, 那么再这个时间上续
		if(user!=null && user.getGmtVipExpire()!=null && user.getGmtVipExpire().after(expire)){
			expire = user.getGmtVipExpire();
		}
		Date vipExpire = DateTools.addDate(expire, date);
		userDao.vipExtend(userId, vipExpire);
		updateTopup(topupDO.getId(),weixinOrderId,TopupStatusEnum.支付成功.getCode());
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
		UserDO user = userDao.getById(userId);
        if(user == null) {
            return;
        }
		if(topupDO.getAmount() == 0){
		    TopListDO forCreate = new TopListDO();
            forCreate.setGmtStart(DateTools.today());
            forCreate.setGmtEnd(DateTools.addMinute(DateTools.today(), minutes));
            forCreate.setUserId(userId);
            forCreate.setOpenId(user.getOpenId());
            forCreate.setSkvId(user.getSkvId());
            forCreate.setPosition(TopListPositionEnum.SKV置顶.getCode());
            topListDao.insert(forCreate);
		}
		TopListDO topListDO = topListDao.getValidByUserId(userId, TopListPositionEnum.充值.getCode());
		Date expire = DateTools.today();
		if(topListDO!=null && topListDO.getGmtEnd()!=null && topListDO.getGmtEnd().after(expire)){
			expire = topListDO.getGmtEnd();
			//如果还是有效期内
			Date gmtEnd = DateTools.addMinute(expire, minutes);
			topListDao.extend(topListDO.getId(), TopListPositionEnum.充值.getCode(), gmtEnd);
		} else {
			Date gmtEnd = DateTools.addMinute(expire, minutes);
			TopListDO forCreate = new TopListDO();
			forCreate.setGmtStart(DateTools.today());
			forCreate.setGmtEnd(gmtEnd);
			forCreate.setUserId(userId);
			forCreate.setOpenId(user.getOpenId());
			forCreate.setSkvId(user.getSkvId());
			forCreate.setPosition(TopListPositionEnum.充值.getCode());
			topListDao.insert(forCreate);
		}
		
		updateTopup(topupDO.getId(),weixinOrderId,TopupStatusEnum.支付成功.getCode());
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
	public PayStatusEnum buy(Long userId, Integer data1, Integer type, ShoppingLevelEnum level) {
		TopupTypeEnum topupType = TopupTypeEnum.getByCode(type);
    	if(topupType == null){
    		return PayStatusEnum.支付失败;
    	}
    	if(topupType.getCode().intValue() == TopupTypeEnum.购买会员.getCode().intValue()){
    		return buyVip(userId,data1);
    	}
    	if(topupType.getCode().intValue() == TopupTypeEnum.购买置顶.getCode().intValue()){
    		return buyZhuangB(userId,data1,level);
    	}
    	return PayStatusEnum.支付失败;
	}

	@Override
	public PayStatusEnum buyVip(Long userId, Integer date) {
		try {
			if(date==null || date <= 0){
				return PayStatusEnum.支付失败;
			}
			Integer coins = priceManager.buyVipUseCoins(date);
			PayStatusEnum payStatus = useCoins(userId, coins);
			if(payStatus.getSuccess()){
				UserDO user = userDao.getById(userId);
				Date expire = DateTools.todayStart();
				//如果已经是VIP, 那么再这个时间上续
				if(user!=null && user.getGmtVipExpire()!=null && user.getGmtVipExpire().after(expire)){
					expire = user.getGmtVipExpire();
				}
				Date vipExpire = DateTools.addDate(expire, date);
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
    public PayStatusEnum songVip(Long userId, Integer date) {
        try {
            if(date==null || date <= 0){
                return PayStatusEnum.支付失败;
            }
            UserDO user = userDao.getById(userId);
            Date expire = DateTools.todayStart();
            //如果已经是VIP, 那么再这个时间上续
            if(user!=null && user.getGmtVipExpire()!=null && user.getGmtVipExpire().after(expire)){
                expire = user.getGmtVipExpire();
            }
            Date vipExpire = DateTools.addDate(expire, date);
            userDao.vipExtend(userId, vipExpire);
            return PayStatusEnum.支付成功;
        } catch (Exception e) {
            e.printStackTrace();
            return PayStatusEnum.支付失败;
        }
    }
	
	@Override
	public PayStatusEnum buyZhuangB(Long userId, Integer minutes, ShoppingLevelEnum level) {
		try {
			if(minutes == null || minutes <= 0){
				return PayStatusEnum.支付失败;
			}
			UserDO userDO = userDao.getById(userId);
            if(userDO == null) {
                return PayStatusEnum.支付失败;
            }
			Integer coins = priceManager.buyZhuangBUseCoins(minutes);
			PriceSetVO freeSet = priceManager.getSkvPriceSetVO(userDO, level);
			if(freeSet != null){
			    coins = 0;
			    minutes = freeSet.getValue();
			    TopListDO forCreate = new TopListDO();
	            forCreate.setGmtStart(DateTools.today());
	            forCreate.setGmtEnd(DateTools.addMinute(DateTools.today(), minutes));
	            forCreate.setUserId(userId);
	            forCreate.setSkvId(userDO.getSkvId());
	            forCreate.setOpenId(userDO.getOpenId());
	            forCreate.setPosition(TopListPositionEnum.SKV置顶.getCode());
	            topListDao.insert(forCreate);
			}
			PayStatusEnum payStatus = useCoins(userId, coins);
			if(payStatus.getSuccess()){
				TopListDO topListDO = topListDao.getValidByUserId(userId, TopListPositionEnum.充值.getCode());
				Date expire = DateTools.today();
				if(topListDO!=null && topListDO.getGmtEnd()!=null && topListDO.getGmtEnd().after(expire)){
					expire = topListDO.getGmtEnd();
					//如果还是有效期内
					Date gmtEnd = DateTools.addMinute(expire, minutes);
					topListDao.extend(topListDO.getId(), TopListPositionEnum.充值.getCode(), gmtEnd);
				} else {
					Date gmtEnd = DateTools.addMinute(expire, minutes);
					TopListDO forCreate = new TopListDO();
					forCreate.setGmtStart(expire);
					forCreate.setGmtEnd(gmtEnd);
					forCreate.setUserId(userId);
					forCreate.setOpenId(userDO.getOpenId());
					forCreate.setPosition(TopListPositionEnum.充值.getCode());
					topListDao.insert(forCreate);
				}
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

    @Override
    public OrderStatisticVO getOrderStatisticVO() {
        OrderStatisticVO orderStatisticVO = new OrderStatisticVO();
        orderStatisticVO.setTotalShare(getTotalShare(DateTools.monthStart(),DateTools.monthEnd(),TopListPositionEnum.分享));
        orderStatisticVO.setTodayShare(getTotalShare(DateTools.todayStart(),DateTools.todayEnd(),TopListPositionEnum.分享));
        orderStatisticVO.setTotalSale(getTotalSale(DateTools.monthStart(),DateTools.monthEnd(),TopupStatusEnum.支付成功,null));
        orderStatisticVO.setTodaySale(getTotalSale(DateTools.todayStart(),DateTools.todayEnd(),TopupStatusEnum.支付成功,null));
        List<OrderAlertVO> statusList = Lists.newArrayList();
        for(TopupTypeEnum type : TopupTypeEnum.getAll()){
            statusList.add(getOrderAlert(type));
        }
        orderStatisticVO.setStatusList(statusList);
        return orderStatisticVO;
    }
    
    private Integer getTotalShare(Date start, Date end, TopListPositionEnum position){
        TopListQueryCondition shareQueryCondition = new TopListQueryCondition();
        shareQueryCondition.setGmtModifyStart(start).setGmtModifyEnd(end).setPosition(position.getCode());
        return topListDao.getCount(shareQueryCondition);
    }
    
    private Integer getTotalSale(Date start, Date end, TopupStatusEnum status, TopupTypeEnum type){
        TopupQueryCondition topupQuery = new TopupQueryCondition();
        topupQuery.setGmtModifyStart(start).setGmtModifyEnd(end).setStatus(status.getCode());
        if(type != null){
            topupQuery.setType(type.getCode());
        }
        List<TopupDO> topupList = topupDao.getByCondition(topupQuery);
        Integer amount = 0;
        if(topupList == null) {
            return amount;
        }
        for(TopupDO topupDO:topupList){
            amount += topupDO.getAmount();
        }
        return amount;
    }
    
    private OrderAlertVO getOrderAlert(TopupTypeEnum type){
        Integer count = getTotalSale(DateTools.todayStart(),DateTools.todayEnd(),TopupStatusEnum.支付成功,type);
        
        OrderAlertVO orderAlertVO = new OrderAlertVO();
        orderAlertVO.setColor(type.getColor());
        orderAlertVO.setCount(count == null?0:count);
        orderAlertVO.setStatusCode(type.getCode().toString());
        orderAlertVO.setStatusDesc(type.getDesc());
        orderAlertVO.setSuccess(count != null);
        orderAlertVO.setMsg(count != null?"查询成功":"系统错误,请速联系管理员!");
        return orderAlertVO;
    }

    @Override
    public void share(UserDO userDO) {
        Date now = DateTools.today();
        TopListDO forCreate = new TopListDO();
        forCreate.setGmtStart(now);
        forCreate.setGmtEnd(now);
        forCreate.setUserId(userDO.getId());
        forCreate.setOpenId(userDO.getOpenId());
        forCreate.setSkvId(userDO.getSkvId());
        forCreate.setPosition(TopListPositionEnum.分享.getCode());
        topListDao.insert(forCreate);
        
    }

    @Override
    public void friend(UserDO userDO) {
        Date now = DateTools.today();
        TopListDO forCreate = new TopListDO();
        forCreate.setGmtStart(now);
        forCreate.setGmtEnd(now);
        forCreate.setUserId(userDO.getId());
        forCreate.setOpenId(userDO.getOpenId());
        forCreate.setSkvId(userDO.getSkvId());
        forCreate.setPosition(TopListPositionEnum.加好友.getCode());
        topListDao.insert(forCreate);
    }
}
