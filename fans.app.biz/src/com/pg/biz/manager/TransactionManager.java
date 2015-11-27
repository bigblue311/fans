package com.pg.biz.manager;

import java.util.List;

import com.pg.biz.model.OrderStatisticVO;
import com.pg.biz.model.OrderVO;
import com.pg.biz.model.PurchaseVO;
import com.pg.dal.model.OrderDO;
import com.pg.dal.model.ProductDO;
import com.pg.dal.model.PurchaseDO;
import com.pg.dal.query.OrderQueryCondition;
import com.pg.dal.query.PurchaseQueryCondition;
import com.victor.framework.dal.basic.Paging;

public interface TransactionManager {
	
    /**
     * 检查库存
     * @param productDO
     * @param quantity
     * @return
     */
    Integer checkQuantity(ProductDO productDO, Integer quantity);
    Integer checkQuantity(ProductDO productDO, Integer originQuantity, Integer quantity);
    Integer checkQuantity(ProductDO productDO, Long purchaseItemId, Integer quantity);
    
	/**
	 * 创建订单
	 * @param orderDO
	 */
	Long createOrder(OrderDO orderDO);
	Long createPurchase(PurchaseDO purchaseDO);
	
	/**
	 * 创建订单
	 * @param orderDO
	 * @param employeeId
	 */
	void createOrder(OrderDO orderDO, Long employeeId);
	void createPurchase(PurchaseDO purchaseDO, Long employeeId, Long customerId);
	void createPurchaseItem(Long purchaseId, Long publishId, Long productId,Integer quantity);
	
	/**
	 * 更新订单
	 * @param orderDO
	 * @param employeeId
	 */
	void updateOrder(OrderDO orderDO,Long employeeId);
	void updatePurchase(PurchaseDO purchaseDO, Long employeeId, Long customerId);
	void updatePurchaseItem(Long id,Integer quantity);
	
	/**
	 * 删除一个购买
	 * @param id
	 * @param employeeId
	 */
	void deletePurchase(Long id,Long employeeId,Long customerId);
	
	/**
	 * 重新计算一笔订单
	 * @param orderId
	 */
	void recalculate(Long orderId);
	Double getFinalPrice(Long publishId, Long productId, Integer quantity);
	
	/**
	 * 根据订单ID获取
	 * @param id
	 * @return
	 */
	OrderDO getOrderDOById(Long id);
	PurchaseDO getPurchaseDOById(Long id);
	OrderVO getOrderVOById(Long id);
	PurchaseVO getPurchaseVOById(Long id);
	
	/**
	 * 根据查询条件获取分页数据
	 * @param queryCondition
	 * @return
	 */
	Paging<OrderDO> getOrderDOPage(OrderQueryCondition queryCondition);
	List<PurchaseDO> getPurchaseDOByCondition(PurchaseQueryCondition queryCondition);
	Paging<PurchaseDO> getPurchaseDOPage(PurchaseQueryCondition queryCondition);
	Paging<PurchaseVO> getPurchaseVOPage(PurchaseQueryCondition queryCondition);
	Paging<OrderVO> getOrderVOPage(OrderQueryCondition queryCondition);
	Integer getOrderCount(OrderQueryCondition queryCondition);
	
	List<OrderDO> getOrderDOList(OrderQueryCondition queryCondition);
	
	List<OrderVO> getOrderVOList(OrderQueryCondition queryCondition);
	OrderStatisticVO getMonthOrderStatistic();
}
