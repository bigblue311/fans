package com.fans.dal.dao;

import java.util.List;

import com.fans.dal.model.QrcodeScanDO;
import com.fans.dal.query.QrcodeQueryCondition;


public interface QrcodeScanDAO {
	/**
	 * 创建对象
	 * @param QrcodeScanDO
	 * @return
	 */
	Long insert(QrcodeScanDO qrcodeScanDO);
	
	/**
	 * 更新对象信息
	 * @param QrcodeScanDO
	 */
	Boolean update(QrcodeScanDO qrcodeScanDO);
	
	/**
	 * 更新扫码人的SKV_ID
	 * @param openId
	 * @param skvId
	 * @return
	 */
	Boolean updateByOpenId(String openId, Long skvId);
	
	/**
	 * 根据ID获取
	 * @param id
	 * @return
	 */
	QrcodeScanDO getById(Long id);
	QrcodeScanDO getByOpenId(String openId);
	QrcodeScanDO getValidUpId(String openId);
	
	/**
     * 根据查询条件获取
     * @param queryCondition
     * @return
     */
    List<QrcodeScanDO> getByCondition(QrcodeQueryCondition queryCondition);
    
    /**
     * 获取分页数据
     * @param queryCondition
     * @return
     */
    List<QrcodeScanDO> getPage(QrcodeQueryCondition queryCondition);
    Integer getCount(QrcodeQueryCondition queryCondition);
}
