package com.fans.dal.dao;

import com.fans.dal.model.SkvUserDO;

public interface SkvUserDAO {
    
    /**
     * 创建对象
     * @param skvUserDO
     * @return
     */
    Long insert(SkvUserDO skvUserDO);
    /**
     * 根据id删除
     * @param id
     * @return
     */
    Boolean delete(Long id);
    /**
     * 更新对象信息
     * @param skvUserDO
     */
    Boolean update(SkvUserDO skvUserDO);
    Boolean updateUsername(String userName,String phone);
    /**
     * 根据手机号码也可能是微信openId
     * @param phone
     * @return
     */
    SkvUserDO getByPhone(String phone);
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
