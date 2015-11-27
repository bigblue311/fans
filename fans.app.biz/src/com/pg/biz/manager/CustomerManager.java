package com.pg.biz.manager;

import java.util.List;

import com.pg.dal.model.CustomerDO;
import com.pg.dal.query.CustomerQueryCondition;
import com.victor.framework.common.shared.Result;
import com.victor.framework.dal.basic.Paging;

public interface CustomerManager {
	
	/**
	 * 创建(注册)一个用户
	 * @param customerDO
	 */
	void create(CustomerDO customerDO);
	
	/**
	 * 更新用户基本信息
	 * @param customerDO
	 */
	void update(CustomerDO customerDO);
	
	/**
	 * 根据ID获取
	 * @param id
	 * @return
	 */
	CustomerDO getById(Long id);
	
	/**
	 * 登录
	 * @param mobile
	 * @param password
	 * @return
	 */
	Result<CustomerDO> login(String mobile, String password);
	
	/**
	 * 检查是否已存在
	 * @param mobile
	 * @return
	 */
	Boolean checkExist(String mobile);
	
	/**
	 * 根据查询条件获取
	 * @param queryCondition
	 * @return
	 */
	List<CustomerDO> getByCondition(CustomerQueryCondition queryCondition);
	
	/**
	 * 获取分页数据
	 * @param queryCondition
	 * @return
	 */
	Paging<CustomerDO> getPage(CustomerQueryCondition queryCondition);
}
