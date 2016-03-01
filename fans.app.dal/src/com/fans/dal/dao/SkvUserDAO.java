package com.fans.dal.dao;

import com.fans.dal.model.SkvUserDO;

public interface SkvUserDAO {
    /**
     * 根据ID获取
     * @param id
     * @return
     */
    SkvUserDO getById(Long id);
    
    /**
     * 根据用户手机和密码获取
     * @param phone
     * @param userPassword
     * @return
     */
    SkvUserDO getByPassword(String phone, String userPassword);
}
