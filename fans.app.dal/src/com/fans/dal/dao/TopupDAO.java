package com.fans.dal.dao;

import java.util.List;

import com.fans.dal.model.TopupDO;
import com.fans.dal.query.TopupQueryCondition;

public interface TopupDAO {
    /**
     * 创建对象
     * @param topupDO
     * @return
     */
    Long insert(TopupDO topupDO);
    
    /**
     * 更新对象信息
     * @param topupDO
     */
    Boolean update(TopupDO topupDO);
    
    /**
     * 根据ID获取
     * @param id
     * @return
     */
    TopupDO getById(Long id);

    /**
     * 根据查询条件获取
     * @param queryCondition
     * @return
     */
    List<TopupDO> getByCondition(TopupQueryCondition queryCondition);
    
    /**
     * 获取分页数据
     * @param queryCondition
     * @return
     */
    List<TopupDO> getPage(TopupQueryCondition queryCondition);
    Integer getCount(TopupQueryCondition queryCondition);
}
