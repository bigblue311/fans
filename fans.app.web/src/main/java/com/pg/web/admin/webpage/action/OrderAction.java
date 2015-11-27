package com.pg.web.admin.webpage.action;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.FormGroup;
import com.pg.biz.manager.CustomerManager;
import com.pg.biz.manager.TransactionManager;
import com.pg.dal.enumerate.OrderStatusEnum;
import com.pg.dal.model.CustomerDO;
import com.pg.dal.model.EmployeeDO;
import com.pg.dal.model.OrderDO;
import com.pg.web.admin.common.AuthenticationToken;

public class OrderAction {
	@Autowired
	private HttpSession session;
	
	@Autowired
	private TransactionManager transactionManager;
	
	@Autowired
	private CustomerManager customerManager;
	
	public void doUpdate(@FormGroup("order") OrderDO orderDO) throws Exception{
		EmployeeDO loginedUser = AuthenticationToken.getLoginedUser(session);
		saveCustomer(orderDO,loginedUser.getId());
		if(orderDO.getId() == null){
			orderDO.setStatus(OrderStatusEnum.提交.getCode());
			transactionManager.createOrder(orderDO,loginedUser.getId());
		} else {
			transactionManager.updateOrder(orderDO,loginedUser.getId());
		}
	}
	
	private void saveCustomer(OrderDO orderDO,Long employeeId){
		if(orderDO!=null && orderDO.getId()!=null){
			CustomerDO customerDO = new CustomerDO();
			customerDO.setId(orderDO.getCustomerId());
			customerDO.setName(orderDO.getCustomerName());
			customerDO.setMobile(orderDO.getCustomerMobile());
			customerDO.setEmployeeId(employeeId);
			customerManager.update(customerDO);
		}
	}
}
