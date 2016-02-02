package com.fans.dal.dao.impl;

import com.fans.dal.dao.QrcodeScanDAO;
import com.fans.dal.model.QrcodeScanDO;
import com.fans.dal.query.QrcodeQueryCondition;
import com.victor.framework.dal.basic.EntityDAO;

public class QrcodeScanDAOImpl extends EntityDAO<QrcodeScanDO,QrcodeQueryCondition> implements QrcodeScanDAO{

	public QrcodeScanDAOImpl() {
		super(QrcodeScanDO.class.getSimpleName());
	}

    @Override
    public QrcodeScanDO getByOpenId(String openId) {
        return super.queryForEntity("getByOpenId", "openId", openId);
    }
}
