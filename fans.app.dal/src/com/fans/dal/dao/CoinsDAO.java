package com.fans.dal.dao;

import java.util.List;

import com.fans.dal.model.CoinsDO;
import com.fans.dal.query.CoinsQueryCondition;

public interface CoinsDAO {
    /**
     * 创建对象
     * @param CoinsDO
     * @return
     */
    Long insert(CoinsDO coinsDO);
    
    /**
     * 根据ID获取
     * @param id
     * @return
     */
    CoinsDO getById(Long id);

    /**
     * 根据查询条件获取
     * @param queryCondition
     * @return
     */
    List<CoinsDO> getByCondition(CoinsQueryCondition queryCondition);
    
    /**
     * 获取分页数据
     * @param queryCondition
     * @return
     */
    List<CoinsDO> getPage(CoinsQueryCondition queryCondition);
    Integer getCount(CoinsQueryCondition queryCondition);
}
