package com.fans.biz.manager;

import java.util.Date;
import java.util.List;

import com.fans.dal.model.UserDO;
import com.fans.dal.query.UserQueryCondition;
import com.victor.framework.dal.basic.Paging;

public interface UserManager {
	
	/**
	 * 创建(注册)一个用户
	 * @param customerDO
	 */
	void create(UserDO userDO);
	
	/**
	 * 更新用户基本信息
	 * @param customerDO
	 */
	void update(UserDO userDO);
	
	/**
	 * 刷新置顶
	 * @param id
	 */
	void refresh(Long id);
	
	/**
	 * 充值
	 * @param id
	 * @param expire
	 */
	void topup(Long id, Date expire);
	
	/**
	 * 根据ID获取
	 * @param id
	 * @return
	 */
	UserDO getById(Long id);
	UserDO getByOpenId(String openId);
	
	/**
	 * 是否可以刷新
	 * @param userDO
	 * @return
	 */
	Boolean canRefresh(UserDO userDO);
	
	/**
	 * 下次刷新时间. 单位秒
	 * @param userDO
	 * @return
	 */
	Integer nextRefresh(UserDO userDO);
	
	/**
	 * 根据查询条件获取
	 * @param queryCondition
	 * @return
	 */
	List<UserDO> getByCondition(UserQueryCondition queryCondition);
	
	/**
	 * 获取分页数据
	 * @param queryCondition
	 * @return
	 */
	Paging<UserDO> getPage(UserQueryCondition queryCondition);
}
