package com.fans.dal.dao.impl;

import com.fans.dal.dao.QrcodeDAO;
import com.fans.dal.model.QrcodeDO;
import com.fans.dal.query.QrcodeQueryCondition;
import com.victor.framework.dal.basic.EntityDAO;

public class QrcodeDAOImpl extends EntityDAO<QrcodeDO,QrcodeQueryCondition> implements QrcodeDAO{

	public QrcodeDAOImpl() {
		super(QrcodeDO.class.getSimpleName());
	}

}
