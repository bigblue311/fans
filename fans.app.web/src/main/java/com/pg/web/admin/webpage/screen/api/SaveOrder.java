package com.pg.web.admin.webpage.screen.api;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Params;
import com.pg.biz.manager.ProductManager;
import com.pg.biz.manager.TransactionManager;
import com.pg.biz.manager.WarehouseManager;
import com.pg.biz.model.PackageVO;
import com.pg.dal.enumerate.OrderStatusEnum;
import com.pg.dal.model.CustomerDO;
import com.pg.dal.model.OrderDO;
import com.pg.dal.model.ProductDO;
import com.pg.dal.model.PurchaseDO;
import com.pg.dal.model.WarehouseDO;
import com.pg.dal.query.ProductQueryCondition;
import com.pg.web.admin.common.AuthenticationToken;
import com.pg.web.admin.model.form.OrderFO;
import com.victor.framework.common.shared.Result;

public class SaveOrder {
	
	@Autowired
	private ProductManager productManager;
	
	@Autowired
	private TransactionManager transactionManager;
	
	@Autowired
	private WarehouseManager warehouseManager;
	
	@Autowired
	private HttpSession session;
	
	
	public Result<Boolean> execute(@Params OrderFO orderFO){
		if(orderFO == null){
			return Result.newInstance(false, "订购无效", false);
		}
		CustomerDO customer = AuthenticationToken.getLoginedCustomer(session);
		if(customer == null){
			return Result.newInstance(false, "登陆已失效", false);
		}
		PackageVO packageVO = productManager.getPackageVOByPublishId(orderFO.getPublishId());
		if(packageVO == null){
			return Result.newInstance(false, "商品已无效", false);
		}
		if(packageVO.getPublishDO() == null){
			return Result.newInstance(false, "商品已无效", false);
		}
		if(packageVO.getPackageDO() == null){
			return Result.newInstance(false, "商品已无效", false);
		}
		WarehouseDO warehouseDO = warehouseManager.getById(orderFO.getWarehouseId());
		if(warehouseDO == null){
			return Result.newInstance(false, "请选择送货地址", false);
		}
		OrderDO orderDO = prepareOrderDO(orderFO,customer);
		Long orderId = transactionManager.createOrder(orderDO);
		PurchaseDO purchaseDO = preparePurchaseDO(orderFO,packageVO,warehouseDO);
		purchaseDO.setOrderId(orderId);
		Long purchaseId = transactionManager.createPurchase(purchaseDO);
		ProductQueryCondition queryCondition = new ProductQueryCondition();
		queryCondition.setPublishId(orderFO.getPublishId());
		List<ProductDO> prodList = productManager.getProductByCondition(queryCondition);
		Map<Long,Integer> map = orderFO.getProductMap();
		for(ProductDO item: prodList){
			Integer quantity = 0;
			if(map.containsKey(item.getId())){
				quantity = map.get(item.getId());
			}
			transactionManager.createPurchaseItem(purchaseId, orderFO.getPublishId(), item.getId(), quantity);
		}
		transactionManager.recalculate(orderId);
		return Result.newInstance(true, "订购提交成功", true);
	}
	
	private OrderDO prepareOrderDO(OrderFO orderFO,CustomerDO customer){
		OrderDO orderDO = new OrderDO();
		orderDO.setCustomerId(customer.getId());
		orderDO.setCustomerName(customer.getName());
		orderDO.setCustomerMobile(customer.getMobile());
		orderDO.setCustomerIdCard(customer.getIdCard());
		orderDO.setComment(orderFO.getComment());
		orderDO.setStatus(OrderStatusEnum.提交.getCode());
		return orderDO;
	}
	
	private PurchaseDO preparePurchaseDO(OrderFO orderFO,PackageVO packageVO,WarehouseDO warehouseDO){
		WarehouseDO addressFromW = packageVO.getWarehouseDO();
		
		PurchaseDO purchaseDO = new PurchaseDO();
		purchaseDO.setPublishId(packageVO.getPublishDO().getId());
		purchaseDO.setName(packageVO.getPackageDO().getName());
		purchaseDO.setTitle(packageVO.getPackageDO().getTitle());
		purchaseDO.setAddressFrom(warehouseManager.getFullAddress(addressFromW));
		purchaseDO.setAddressTo(warehouseManager.getFullAddress(warehouseDO));
		purchaseDO.setKeeper(warehouseDO.getKeeper());
		purchaseDO.setKeeperIdCard(warehouseDO.getKeeperIdCard());
		purchaseDO.setPhone(warehouseDO.getPhone());
		purchaseDO.setMobile(warehouseDO.getMobile());
		purchaseDO.setComment(orderFO.getComment());
		return purchaseDO;
	}
}