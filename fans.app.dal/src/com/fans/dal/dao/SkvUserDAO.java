package com.fans.dal.dao;

import com.fans.dal.model.SkvUserDO;

public interface SkvUserDAO {
    /**
     * 根据ID获取
     * @param id
     * @return
     */
    SkvUserDO getById(Long id);
}
