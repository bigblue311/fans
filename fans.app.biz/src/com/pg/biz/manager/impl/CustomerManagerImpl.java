package com.pg.biz.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.pg.biz.manager.CustomerManager;
import com.pg.dal.dao.CustomerDAO;
import com.pg.dal.model.CustomerDO;
import com.pg.dal.query.CustomerQueryCondition;
import com.victor.framework.common.shared.Result;
import com.victor.framework.common.tools.LogTools;
import com.victor.framework.common.tools.MD5;
import com.victor.framework.common.tools.StringTools;
import com.victor.framework.dal.basic.Paging;

public class CustomerManagerImpl implements CustomerManager{

	LogTools logger = new LogTools(CustomerManagerImpl.class);
	
	@Autowired
	private CustomerDAO customerDAO;
	
	@Override
	public void create(CustomerDO customerDO) {
		customerDAO.insert(customerDO);
	}

	@Override
	public void update(CustomerDO customerDO) {
		customerDAO.update(customerDO);
	}

	@Override
	public CustomerDO getById(Long id) {
		return customerDAO.getById(id);
	}

	@Override
	public Result<CustomerDO> login(String mobile, String password) {
		if(StringTools.isAnyEmpty(mobile,password)){
			return Result.newInstance(null, "用户名或密码为空", false);
		}
		password = MD5.getMD5(password);
		CustomerDO customerDO = customerDAO.login(mobile, password);
		if(customerDO == null){
			return Result.newInstance(null, "用户名不存在或密码错误", false);
		}
		return Result.newInstance(customerDO, "登录成功", true);
	}

	@Override
	public Boolean checkExist(String mobile) {
		CustomerQueryCondition queryCondition = new CustomerQueryCondition();
		queryCondition.setMobile(mobile);
		List<CustomerDO> list = customerDAO.getByCondition(queryCondition);
		return !list.isEmpty();
	}

	@Override
	public List<CustomerDO> getByCondition(CustomerQueryCondition queryCondition) {
		return customerDAO.getByCondition(queryCondition);
	}

	@Override
	public Paging<CustomerDO> getPage(CustomerQueryCondition queryCondition) {
		int totalSize = customerDAO.getCount(queryCondition);
		@SuppressWarnings("unchecked")
		Paging<CustomerDO> page = queryCondition.getPaging(totalSize, 5);
		List<CustomerDO> list = customerDAO.getPage(queryCondition);
		page.setData(list);
		return page;
	}
}
