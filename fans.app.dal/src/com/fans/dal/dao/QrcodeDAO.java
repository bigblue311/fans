package com.fans.dal.dao;

import java.util.List;

import com.fans.dal.model.QrcodeDO;
import com.fans.dal.query.QrcodeQueryCondition;


public interface QrcodeDAO {
	/**
	 * 创建对象
	 * @param QrcodeDO
	 * @return
	 */
	Long insert(QrcodeDO qrcodeDO);
	
	/**
	 * 更新对象信息
	 * @param QrcodeDO
	 */
	Boolean update(QrcodeDO qrcodeDO);
	
	/**
	 * 根据ID获取
	 * @param id
	 * @return
	 */
	QrcodeDO getById(Long id);
	
	/**
     * 根据查询条件获取
     * @param queryCondition
     * @return
     */
    List<QrcodeDO> getByCondition(QrcodeQueryCondition queryCondition);
    
    /**
     * 获取分页数据
     * @param queryCondition
     * @return
     */
    List<QrcodeDO> getPage(QrcodeQueryCondition queryCondition);
    Integer getCount(QrcodeQueryCondition queryCondition);
}
