package com.fans.dal.dao;

import java.util.List;

import com.fans.dal.model.UserDO;
import com.fans.dal.query.UserQueryCondition;

public interface UserDAO {
    /**
     * 创建对象
     * @param userDO
     * @return
     */
    Long insert(UserDO userDO);
    
    /**
     * 更新对象信息
     * @param customerDO
     */
    Boolean update(UserDO userDO);
    
    /**
     * 根据ID获取
     * @param id
     * @return
     */
    UserDO getById(Long id);
    UserDO getByOpenId(String openId);

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
    List<UserDO> getPage(UserQueryCondition queryCondition);
    Integer getCount(UserQueryCondition queryCondition);
}
