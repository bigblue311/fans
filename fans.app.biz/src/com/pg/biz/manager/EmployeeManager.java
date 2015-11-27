package com.pg.biz.manager;

import java.util.List;

import com.pg.dal.model.EmployeeDO;
import com.victor.framework.common.shared.Result;

public interface EmployeeManager {
	
	/**
	 * 创建一个员工
	 * @param employee
	 */
	void create(EmployeeDO employeeDO);
	
	/**
	 * 更新员工基本信息
	 * @param employee
	 */
	void update(EmployeeDO employeeDO);
	
	/**
	 * 删除一个员工
	 * @param id
	 */
	void delete(Long id);
	
	/**
	 * 根据ID获取
	 * @param id
	 * @return
	 */
	EmployeeDO getById(Long id);
	
	/**
	 * 获取所有
	 * @return
	 */
	List<EmployeeDO> getAll();
	
	/**
	 * 登录
	 * @param name
	 * @param password
	 * @return
	 */
	Result<EmployeeDO> login(EmployeeDO employeeDO);
	
	/**
	 * 检测密码
	 * @param employeeDO
	 * @return
	 */
	Result<Boolean> checkPwd(EmployeeDO employeeDO);
	
	/**
	 * 检查是否已存在
	 * @param name
	 * @return
	 */
	Boolean checkExist(String name);
}
