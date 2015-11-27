package com.pg.biz.manager.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.pg.biz.manager.TransactionManager;
import com.pg.biz.model.OrderAlertVO;
import com.pg.biz.model.OrderStatisticVO;
import com.pg.biz.model.OrderVO;
import com.pg.biz.model.PurchaseVO;
import com.pg.dal.dao.CustomerDAO;
import com.pg.dal.dao.EmployeeDAO;
import com.pg.dal.dao.OpLogDAO;
import com.pg.dal.dao.OrderDAO;
import com.pg.dal.dao.ProductDAO;
import com.pg.dal.dao.PublishDAO;
import com.pg.dal.dao.PurchaseDAO;
import com.pg.dal.dao.PurchaseItemDAO;
import com.pg.dal.enumerate.OrderStatusEnum;
import com.pg.dal.model.CustomerDO;
import com.pg.dal.model.EmployeeDO;
import com.pg.dal.model.OpLogDO;
import com.pg.dal.model.OrderDO;
import com.pg.dal.model.ProductDO;
import com.pg.dal.model.PublishDO;
import com.pg.dal.model.PurchaseDO;
import com.pg.dal.model.PurchaseItemDO;
import com.pg.dal.query.OpLogQueryCondition;
import com.pg.dal.query.OrderQueryCondition;
import com.pg.dal.query.ProductQueryCondition;
import com.pg.dal.query.PurchaseItemQueryCondition;
import com.pg.dal.query.PurchaseQueryCondition;
import com.victor.framework.common.tools.DateTools;
import com.victor.framework.common.tools.StringTools;
import com.victor.framework.dal.basic.Paging;

public class TransactionManagerImpl implements TransactionManager{

	@Autowired
	private OrderDAO orderDAO;
	
	@Autowired
	private OpLogDAO opLogDAO;
	
	@Autowired
	private EmployeeDAO employeeDAO;
	
	@Autowired
	private CustomerDAO customerDAO;
	
	@Autowired
	private PublishDAO publishDAO;
	
	@Autowired
	private ProductDAO productDAO;
	
	@Autowired
	private PurchaseDAO purchaseDAO;
	
	@Autowired
	private PurchaseItemDAO purchaseItemDAO;
	
	@Override
	public Long createOrder(OrderDO orderDO) {
		return orderDAO.insert(orderDO);
	}
	
	@Override
	public Long createPurchase(PurchaseDO purchaseDO) {
		return purchaseDAO.insert(purchaseDO);
	}
	
	@Override
	public void createOrder(OrderDO orderDO, Long employeeId) {
		if(orderDO != null){
			Long orderId = orderDAO.insert(orderDO);
			
			OpLogDO opLogDO = new OpLogDO();
			opLogDO.setEmployeeId(employeeId);
			opLogDO.setOrderId(orderId);
			CustomerDO customerDO = customerDAO.getById(orderDO.getCustomerId());
			if(customerDO == null){
				return;
			}
			String msg = getEmployeeName(employeeId)+"为客户"+customerDO.getName()+"["+customerDO.getMobile()+"]提交了订单";
			opLogDO.setAction(msg);
			opLogDAO.insert(opLogDO);
		}
	}
	
	@Override
	public void createPurchase(PurchaseDO purchaseDO, Long employeeId, Long customerId) {
		Long purchaseId = purchaseDAO.insert(purchaseDO);
		purchaseDO.setId(purchaseId);
		createPurchaseItem(purchaseDO);
		Long orderId = purchaseDO.getOrderId();
		recalculate(orderId);
		
		OpLogDO opLogDO = new OpLogDO();
		opLogDO.setEmployeeId(employeeId);
		opLogDO.setOrderId(orderId);
		CustomerDO customerDO = customerDAO.getById(customerId);
		if(customerDO == null){
			return;
		}
		String msg = getEmployeeName(employeeId)+"为客户"+customerDO.getName()+"["+customerDO.getMobile()+"]购买了"+purchaseDO.getName();
		opLogDO.setAction(msg);
		opLogDAO.insert(opLogDO);
	}
	
	@Override
	public void createPurchaseItem(Long purchaseId, Long publishId, Long productId, Integer quantity) {
		ProductDO productDO = productDAO.getById(productId);
		PurchaseItemDO purchaseItemDO = new PurchaseItemDO();
		purchaseItemDO.setName(productDO.getName());
		purchaseItemDO.setTitle(productDO.getTitle());
		purchaseItemDO.setBrandId(productDO.getBrandId());
		purchaseItemDO.setCategoryId(productDO.getCategoryId());
		quantity = checkQuantity(productDO,quantity);
		if(quantity != null){
		    if(productDO.getSu() != null){
		        purchaseItemDO.setMsu(productDO.getSu()*quantity);
		    }
		    if(productDO.getCubage() != null) {
		        purchaseItemDO.setMcubage(productDO.getCubage()*quantity);
		    }
		    if(productDO.getWeight() != null) {
		        purchaseItemDO.setMweight(productDO.getWeight()*quantity);
		    }
		}
		purchaseItemDO.setPrice(getFinalPrice(publishId,productId,quantity));
		purchaseItemDO.setQuantity(quantity);
		purchaseItemDO.setPurchaseId(purchaseId);
		purchaseItemDO.setProductId(productDO.getId());
		purchaseItemDAO.insert(purchaseItemDO);
		updateVolume(productDO, quantity);
	}
	
	private void updateVolume(ProductDO productDO, Integer quantity){
	    if(productDO == null || productDO.getVolume() == null){
            return;
        }
        Integer volume = productDO.getVolume();
        if(volume <= quantity){
            volume = 0;
        } else {
            volume = volume - quantity;
        }
        productDO.setVolume(volume);
        productDAO.update(productDO);
	}
	
	private void updateVolume(ProductDO productDO, Integer originQuantity, Integer quantity){
        if(productDO == null || productDO.getVolume() == null){
            return;
        }
        Integer volume = productDO.getVolume();
        if(originQuantity!=null){
            volume = volume + originQuantity;
        }
        if(volume <= quantity){
            volume = 0;
        } else {
            volume = volume - quantity;
        }
        productDO.setVolume(volume);
        productDAO.update(productDO);
    }
	
	@Override
	public Integer checkQuantity(ProductDO productDO, Integer quantity){
	    if(productDO == null || productDO.getVolume() == null){
	        return quantity;
	    }
	    Integer volume = productDO.getVolume();
	    if(volume <= quantity){
	        return volume;
	    } else {
	        return quantity;
	    }
	}
	
	@Override
	public Integer checkQuantity(ProductDO productDO,Integer originQuantity, Integer quantity){
        if(productDO == null || productDO.getVolume() == null){
            return quantity;
        }
        Integer volume = productDO.getVolume();
        if(originQuantity!=null){
            volume = volume + originQuantity;
        }
        if(volume <= quantity){
            return volume;
        } else {
            return quantity;
        }
    }
	
	@Override
    public Integer checkQuantity(ProductDO productDO, Long purchaseItemId, Integer quantity) {
        PurchaseItemDO purchaseItemDO = purchaseItemDAO.getById(purchaseItemId);
        if(purchaseItemDO == null){
            return checkQuantity(productDO,quantity);
        } else {
            return checkQuantity(productDO,purchaseItemDO.getQuantity(),quantity);
        }
    }
	
	private void createPurchaseItem(PurchaseDO purchaseDO){
		if(purchaseDO == null){
			return;
		}
		Long publishId = purchaseDO.getPublishId();
		if(publishId == null){
			return;
		}
		PublishDO publishDO = publishDAO.getById(publishId);
		if(publishDO == null){
			return;
		}
		Long packageId = publishDO.getPackageId();
		if(packageId == null){
			return;
		}
		ProductQueryCondition query = new ProductQueryCondition();
		query.setPackageId(packageId);
		List<ProductDO> list = productDAO.getByCondition(query);
		for(ProductDO productDO : list){
			PurchaseItemDO purchaseItemDO = new PurchaseItemDO();
			purchaseItemDO.setName(productDO.getName());
			purchaseItemDO.setTitle(productDO.getTitle());
			purchaseItemDO.setBrandId(productDO.getBrandId());
			purchaseItemDO.setCategoryId(productDO.getCategoryId());
			purchaseItemDO.setMsu(0d);
			purchaseItemDO.setPrice(getFinalPrice(publishId,productDO.getId(),0));
			purchaseItemDO.setQuantity(0);
			purchaseItemDO.setMcubage(0d);
			purchaseItemDO.setMweight(0d);
			purchaseItemDO.setPurchaseId(purchaseDO.getId());
			purchaseItemDO.setProductId(productDO.getId());
			purchaseItemDAO.insert(purchaseItemDO);
		}
	}

	@Override
	public void updateOrder(OrderDO orderDO,Long employeeId) {
		if(orderDO != null){
			OpLogDO opLogDO = new OpLogDO();
			opLogDO.setEmployeeId(employeeId);
			opLogDO.setOrderId(orderDO.getId());
			opLogDO.setAction(getOpLogAction(orderDO,employeeId));
			opLogDAO.insert(opLogDO);
			orderDAO.update(orderDO);
		}
	}
	
	@Override
	public void updatePurchase(PurchaseDO purchaseDO, Long employeeId, Long customerId) {
		if(purchaseDO != null){
			OpLogDO opLogDO = new OpLogDO();
			opLogDO.setEmployeeId(employeeId);
			opLogDO.setOrderId(purchaseDO.getId());
			opLogDO.setAction(getOpLogAction(purchaseDO,employeeId,customerId));
			opLogDAO.insert(opLogDO);
			purchaseDAO.update(purchaseDO);
			recalculate(purchaseDO.getOrderId());
		}
	}
	
	@Override
	public void updatePurchaseItem(Long id,Integer quantity){
		if(id == null || quantity == null || quantity < 0){
			return;
		}
		PurchaseItemDO purchaseItemDO = purchaseItemDAO.getById(id);
		if(purchaseItemDO == null){
			return;
		}
		PurchaseDO purchaseDO = purchaseDAO.getById(purchaseItemDO.getPurchaseId());
		if(purchaseDO == null){
			return;
		}
		ProductDO productDO = productDAO.getById(purchaseItemDO.getProductId());
		if(productDO == null){
            return;
        }
		quantity = checkQuantity(productDO,purchaseItemDO.getQuantity(), quantity);
		purchaseItemDO.setId(id);
		purchaseItemDO.setQuantity(quantity);
		Double finalPrice = getFinalPrice(purchaseDO.getPublishId(),purchaseItemDO.getProductId(),quantity);
		purchaseItemDO.setPrice(finalPrice);
		purchaseItemDAO.update(purchaseItemDO);
		updateVolume(productDO,purchaseItemDO.getQuantity(), quantity);
	}
	
	@Override
	public OrderDO getOrderDOById(Long id) {
		return orderDAO.getById(id);
	}
	
	@Override
	public PurchaseDO getPurchaseDOById(Long id) {
		return purchaseDAO.getById(id);
	}
	
	@Override
	public OrderVO getOrderVOById(Long id) {
		return orderDO2VO(getOrderDOById(id));
	}
	
	@Override
	public PurchaseVO getPurchaseVOById(Long id) {
		return purchaseDO2VO(purchaseDAO.getById(id));
	}

	@Override
	public Paging<OrderDO> getOrderDOPage(OrderQueryCondition queryCondition) {
		int totalSize = orderDAO.getCount(queryCondition);
		@SuppressWarnings("unchecked")
		Paging<OrderDO> page = queryCondition.getPaging(totalSize, 5);
		List<OrderDO> list = orderDAO.getPage(queryCondition);
		page.setData(list);
		return page;
	}
	
	@Override
	public Paging<PurchaseDO> getPurchaseDOPage(PurchaseQueryCondition queryCondition) {
		int totalSize = purchaseDAO.getCount(queryCondition);
		@SuppressWarnings("unchecked")
		Paging<PurchaseDO> page = queryCondition.getPaging(totalSize, 5);
		List<PurchaseDO> list = purchaseDAO.getPage(queryCondition);
		page.setData(list);
		return page;
	}
	
	@Override
	public Paging<PurchaseVO> getPurchaseVOPage(PurchaseQueryCondition queryCondition) {
		int totalSize = purchaseDAO.getCount(queryCondition);
		@SuppressWarnings("unchecked")
		Paging<PurchaseVO> page = queryCondition.getPaging(totalSize, 5);
		List<PurchaseDO> list = purchaseDAO.getPage(queryCondition);
		List<PurchaseVO> voList = Lists.transform(list, new Function<PurchaseDO,PurchaseVO>(){

			@Override
			public PurchaseVO apply(PurchaseDO purchaseDO) {
				return purchaseDO2VO(purchaseDO);
			}
			
		});
		page.setData(voList);
		return page;
	}
	
	@Override
	public Paging<OrderVO> getOrderVOPage(OrderQueryCondition queryCondition) {
		int totalSize = orderDAO.getCount(queryCondition);
		@SuppressWarnings("unchecked")
		Paging<OrderVO> page = queryCondition.getPaging(totalSize, 5);
		List<OrderDO> doList = orderDAO.getPage(queryCondition);
		List<OrderVO> voList = Lists.transform(doList, new Function<OrderDO,OrderVO>(){

			@Override
			public OrderVO apply(OrderDO orderDO) {
				return orderDO2VO(orderDO);
			}
			
		});
		page.setData(voList);
		return page;
	}
	
	private OrderVO orderDO2VO(OrderDO orderDO){
		if(orderDO == null){
			return null;
		}
		OrderVO orderVO = new OrderVO();
		orderVO.setOrderDO(orderDO);
		CustomerDO customerDO = customerDAO.getById(orderDO.getCustomerId());
		if(customerDO == null){
			return null;
		}
		orderVO.setCustomerDO(customerDO);
		OpLogQueryCondition query = new OpLogQueryCondition();
		query.setOrderId(orderDO.getId());
		List<OpLogDO> opList = opLogDAO.getByCondition(query);
		orderVO.setOpLogList(opList);
		
		PurchaseQueryCondition queryCondition = new PurchaseQueryCondition();
		queryCondition.setOrderId(orderDO.getId());
		List<PurchaseDO> purchaseList = purchaseDAO.getByCondition(queryCondition);
		List<PurchaseVO> purchaseVOList = Lists.transform(purchaseList, new Function<PurchaseDO,PurchaseVO>(){

			@Override
			public PurchaseVO apply(PurchaseDO purchaseDO) {
				return purchaseDO2VO(purchaseDO);
			}
			
		});
		orderVO.setPurchaseList(purchaseVOList);
		return orderVO;
	}
	
	private PurchaseVO purchaseDO2VO(PurchaseDO purchaseDO){
		if(purchaseDO == null){
			return null;
		}
		PurchaseVO purchaseVO = new PurchaseVO();
		purchaseVO.setId(purchaseDO.getId());
		purchaseVO.setGmtCreate(purchaseDO.getGmtCreate());
		purchaseVO.setGmtModify(purchaseDO.getGmtModify());
		purchaseVO.setOrderId(purchaseDO.getOrderId());
		purchaseVO.setName(purchaseDO.getName());
		purchaseVO.setTitle(purchaseDO.getTitle());
		purchaseVO.setPublishId(purchaseDO.getPublishId());
		purchaseVO.setAddressFrom(purchaseDO.getAddressFrom());
		purchaseVO.setAddressTo(purchaseDO.getAddressTo());
		purchaseVO.setKeeper(purchaseDO.getKeeper());
		purchaseVO.setKeeperIdCard(purchaseDO.getKeeperIdCard());
		purchaseVO.setPhone(purchaseDO.getPhone());
		purchaseVO.setMobile(purchaseDO.getMobile());
		purchaseVO.setTransportFee(purchaseDO.getTransportFee());
		purchaseVO.setTransportCode(purchaseDO.getTransportCode());
		purchaseVO.setComment(purchaseDO.getComment());
		
		Long orderId = purchaseDO.getOrderId();
		OrderDO orderDO = orderDAO.getById(orderId);
		if(orderDO != null){
			purchaseVO.setCustomerName(orderDO.getCustomerName());
			purchaseVO.setCustomerMobile(orderDO.getCustomerMobile());
			purchaseVO.setCustomerIdCard(orderDO.getCustomerIdCard());
			purchaseVO.setStatus(orderDO.getStatus());
		}
		
		PurchaseItemQueryCondition purchaseItemQueryCondition = new PurchaseItemQueryCondition();
		purchaseItemQueryCondition.setPurchaseId(purchaseDO.getId());
		List<PurchaseItemDO> purchaseItemlist = purchaseItemDAO.getByCondition(purchaseItemQueryCondition);
		purchaseVO.setItemList(purchaseItemlist);
		
		Long publishId = purchaseDO.getPublishId();
		PublishDO publishDO = publishDAO.getById(publishId);
		if(publishDO!=null){
			purchaseVO.setLimitBuyPrice(publishDO.getLimitBuyPrice());
			purchaseVO.setLimitBuyQuantity(publishDO.getLimitBuyQuantity());
		}
		
		return purchaseVO;
	}

	private String getEmployeeName(Long employeeId){
		EmployeeDO employee = employeeDAO.getById(employeeId);
		if(employee == null){
			return "[神秘人]";
		} else {
			return employee.getName();
		}
	}
	
	private String getOpLogAction(PurchaseDO purchaseDO, Long employeeId, Long customerId){
		if(purchaseDO == null){
			return "";
		}
		Long id = purchaseDO.getId();
		CustomerDO customerDO = customerDAO.getById(customerId);
		if(customerDO == null){
			return "";
		}
		PurchaseDO originDO = purchaseDAO.getById(id);
		String msg = getEmployeeName(employeeId)+"为客户"+customerDO.getName()+"["+customerDO.getMobile()+"]操作了"+purchaseDO.getName();
		if(StringTools.isNotEmpty(purchaseDO.getAddressTo())){
			msg += "收货仓库地址["+originDO.getAddressTo()+"]:"+purchaseDO.getAddressTo()+";";
		}
		if(StringTools.isNotEmpty(purchaseDO.getKeeper())){
			msg += "收货仓库联系人["+originDO.getKeeper()+"]:"+purchaseDO.getKeeper()+";";
		}
		if(StringTools.isNotEmpty(purchaseDO.getPhone())){
			msg += "收货仓库联系人电话["+originDO.getPhone()+"]:"+purchaseDO.getPhone()+";";
		}
		if(StringTools.isNotEmpty(purchaseDO.getMobile())){
			msg += "收货仓库联系人手机["+originDO.getMobile()+"]:"+purchaseDO.getMobile()+";";
		}
		if(purchaseDO.getTransportFee()!=null){
			msg += "物流费["+originDO.getTransportFee()+"]:"+purchaseDO.getTransportFee()+";";
		}
		if(StringTools.isNotEmpty(purchaseDO.getTransportCode())){
			msg += "物流编号["+originDO.getTransportCode()+"]:"+purchaseDO.getTransportCode()+";";
		}
		return msg;
	}
	
	private String getOpLogAction(OrderDO orderDO, Long employeeId){
		if(orderDO == null){
			return "";
		}
		Long id = orderDO.getId();
		OrderDO originDO = orderDAO.getById(id);
		CustomerDO customerDO = customerDAO.getById(orderDO.getCustomerId());
		if(customerDO == null){
			return "";
		}
		String msg = getEmployeeName(employeeId)+"为客户"+customerDO.getName()+"["+customerDO.getMobile()+"]操作了";
		if(orderDO.getCustomerName()!=null && !orderDO.getCustomerName().equals(originDO.getCustomerName())){
			msg += "客户姓名["+originDO.getCustomerName()+"]:"+orderDO.getCustomerName()+";";
		}
		if(orderDO.getCustomerMobile()!=null && !orderDO.getCustomerMobile().equals(originDO.getCustomerMobile())){
			msg += "客户电话["+originDO.getCustomerMobile()+"]:"+orderDO.getCustomerMobile()+";";
		}
		if(orderDO.getCustomerIdCard()!=null && !orderDO.getCustomerIdCard().equals(originDO.getCustomerIdCard())){
			msg += "客户身份证["+originDO.getCustomerIdCard()+"]:"+orderDO.getCustomerIdCard()+";";
		}
		if(orderDO.getDeposit()!=null && orderDO.getDeposit() != originDO.getDeposit()){
			msg += "定金["+originDO.getDeposit()+"]:"+orderDO.getDeposit()+";";
		}
		if(orderDO.getTotalPrice()!=null && orderDO.getTotalPrice() != originDO.getTotalPrice()){
			msg += "单价["+originDO.getTotalPrice()+"]:"+orderDO.getTotalPrice()+";";
		}
		if(orderDO.getTransportFee()!=null && orderDO.getTransportFee() != originDO.getTransportFee()){
			msg += "物流费["+originDO.getTransportFee()+"]:"+orderDO.getTransportFee()+";";
		}
		if(StringTools.isNotEmpty(orderDO.getStatus()) && !orderDO.getStatus().equals(originDO.getStatus())){
			try {
				String old = OrderStatusEnum.getByCode(originDO.getStatus()).getDesc();
				String cur = OrderStatusEnum.getByCode(orderDO.getStatus()).getDesc();
				msg += "状态["+old+"]:"+cur+";";
			} catch (Exception e) {
				msg += "状态异常:;";
			}
		}
		if(StringTools.isNotEmpty(orderDO.getComment()) && !orderDO.getComment().equals(originDO.getComment())){
			msg += "备注["+originDO.getComment()+"]:"+orderDO.getComment()+";";
		}
		return msg;
	}

	@Override
	public void deletePurchase(Long id,Long employeeId,Long customerId) {
		PurchaseDO purchaseDO = purchaseDAO.getById(id);
		recalculate(purchaseDO.getOrderId());
		OpLogDO opLogDO = new OpLogDO();
		opLogDO.setEmployeeId(employeeId);
		opLogDO.setOrderId(purchaseDO.getOrderId());
		CustomerDO customerDO = customerDAO.getById(customerId);
		if(customerDO == null){
			return;
		}
		String msg = getEmployeeName(employeeId)+"为客户["+customerDO.getMobile()+"]删除了"+purchaseDO.getName();
		opLogDO.setAction(msg);
		opLogDAO.insert(opLogDO);
		purchaseDAO.delete(id);
		PurchaseItemQueryCondition purchaseItemQueryCondition = new PurchaseItemQueryCondition();
		purchaseItemQueryCondition.setPurchaseId(purchaseDO.getId());
		List<PurchaseItemDO> purchaseItemlist = purchaseItemDAO.getByCondition(purchaseItemQueryCondition);
		for(PurchaseItemDO purchaseItemDO : purchaseItemlist){
			purchaseItemDAO.delete(purchaseItemDO.getId());
		}
	}
	
	@Override
	public void recalculate(Long orderId){
		OrderDO order = orderDAO.getById(orderId);
		if(order == null){
			return;
		}
		PurchaseQueryCondition queryCondition = new PurchaseQueryCondition();
		queryCondition.setOrderId(orderId);
		List<PurchaseDO> list = purchaseDAO.getByCondition(queryCondition);
		Double total = 0.0d;
		Double totalTransportFee = 0.0d;
		for(PurchaseDO purchaseDO : list){
			if(purchaseDO == null){
				continue;
			}
			PurchaseItemQueryCondition purchaseItemQueryCondition = new PurchaseItemQueryCondition();
			purchaseItemQueryCondition.setPurchaseId(purchaseDO.getId());
			List<PurchaseItemDO> purchaseItemlist = purchaseItemDAO.getByCondition(purchaseItemQueryCondition);
			for(PurchaseItemDO purchaseItemDO : purchaseItemlist){
				if(purchaseItemDO!=null && purchaseItemDO.getPrice()!=null && purchaseItemDO.getQuantity()!=null){
					total += purchaseItemDO.getPrice() * purchaseItemDO.getQuantity();
				}
			}
			if(purchaseDO.getTransportFee()!=null){
				totalTransportFee += purchaseDO.getTransportFee();
			}
		}
		order.setTotalPrice(total);
		order.setTransportFee(totalTransportFee);
		orderDAO.update(order);
	}
	
	@Override
	public Double getFinalPrice(Long publishId, Long productId, Integer quantity){
		if(publishId == null || productId == null){
			return 0d;
		}
		PublishDO publishDO = publishDAO.getById(publishId);
		ProductDO productDO = productDAO.getById(productId);
		if(publishDO == null || productDO == null){
			return 0d;
		}
		quantity = checkQuantity(productDO,quantity);
		Double finalDiscount = 1.0;
		if(publishDO != null && publishDO.getDiscount() != null){
			finalDiscount = finalDiscount * publishDO.getDiscount();
		}
		if(quantity == null || quantity <= 100){
			BigDecimal finalPrice = new BigDecimal(productDO.getPrice100() * finalDiscount);
			return finalPrice.setScale(2, RoundingMode.HALF_UP).doubleValue();
		}
		if(quantity>100 && quantity<=200){
			BigDecimal finalPrice = new BigDecimal(productDO.getPrice200() * finalDiscount);
			return finalPrice.setScale(2, RoundingMode.HALF_UP).doubleValue();
		}
		if(quantity>200 && quantity<=800){
			BigDecimal finalPrice = new BigDecimal(productDO.getPrice800() * finalDiscount);
			return finalPrice.setScale(2, RoundingMode.HALF_UP).doubleValue();
		}
		if(quantity>800 && quantity<=2000){
			BigDecimal finalPrice = new BigDecimal(productDO.getPrice2000() * finalDiscount);
			return finalPrice.setScale(2, RoundingMode.HALF_UP).doubleValue();
		}
		else{
			BigDecimal finalPrice = new BigDecimal(productDO.getPrice3500() * finalDiscount);
			return finalPrice.setScale(2, RoundingMode.HALF_UP).doubleValue();
		}
	}

	@Override
	public OrderStatisticVO getMonthOrderStatistic() {
		OrderStatisticVO orderStatisticVO = new OrderStatisticVO();
		List<OrderAlertVO> alertList = Lists.newArrayList();
		OrderQueryCondition query = new OrderQueryCondition();
		String[] statusValid = new String[]{OrderStatusEnum.确认.getCode(),
											OrderStatusEnum.发货.getCode(),
											OrderStatusEnum.收货.getCode(),
											OrderStatusEnum.结算.getCode()};
		
		query.setGmtCreateStart(DateTools.monthStart()).setGmtCreateEnd(DateTools.monthEnd());
		query.status(statusValid);
		
		Integer totalCount = orderDAO.getCount(query);
		Double totalSale = orderDAO.getTotalSale(query);
		Double totalDeposit = orderDAO.getTotalDeposit(query);
		
		orderStatisticVO.setTotalCount(totalCount);
		orderStatisticVO.setTotalSale(totalSale);
		orderStatisticVO.setTotalDeposit(totalDeposit);
		
		OrderQueryCondition queryMonth = new OrderQueryCondition();
		queryMonth.setGmtCreateStart(DateTools.monthStart()).setGmtCreateEnd(DateTools.monthEnd());
		for(OrderStatusEnum status : OrderStatusEnum.getAll()){
			queryMonth.setStatus(status.getCode());
			Integer statusCount = orderDAO.getCount(queryMonth);
			OrderAlertVO orderAlert = new OrderAlertVO();
			if(statusCount == null){
				orderAlert.setSuccess(false);
				orderAlert.setMsg("系统错误,请速联系管理员!");
			} else {
				orderAlert.setSuccess(true);
				orderAlert.setMsg("查询成功");
			}
			orderAlert.setCount(statusCount == null?0:statusCount.intValue());
			orderAlert.setColor(status.getColor());
			orderAlert.setStatusCode(status.getCode());
			orderAlert.setStatusDesc(status.getDesc());
			alertList.add(orderAlert);
		}
		orderStatisticVO.setStatusList(alertList);
		return orderStatisticVO;
	}

	@Override
	public List<OrderDO> getOrderDOList(OrderQueryCondition queryCondition) {
		return orderDAO.getByCondition(queryCondition);
	}
	
	@Override
	public List<OrderVO> getOrderVOList(OrderQueryCondition queryCondition) {
		List<OrderDO> list = getOrderDOList(queryCondition);
		return Lists.transform(list, new Function<OrderDO,OrderVO>(){

			@Override
			public OrderVO apply(OrderDO orderDO) {
				return orderDO2VO(orderDO);
			}
			
		});
	}

	@Override
	public List<PurchaseDO> getPurchaseDOByCondition(PurchaseQueryCondition queryCondition) {
		return purchaseDAO.getByCondition(queryCondition);
	}

	@Override
	public Integer getOrderCount(OrderQueryCondition queryCondition) {
		return orderDAO.getCount(queryCondition);
	}
}