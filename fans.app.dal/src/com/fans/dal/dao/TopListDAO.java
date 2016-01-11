package com.fans.dal.dao;

import java.util.Date;
import java.util.List;

import com.fans.dal.model.TopListDO;
import com.fans.dal.query.TopListQueryCondition;

public interface TopListDAO {
    /**
     * 创建对象
     * @param topupDO
     * @return
     */
    Long insert(TopListDO topListDO);
    
    /**
     * 过期一个用户的置顶
     * @param userId
     */
    void expire(Long userId, Integer position);
    
    /**
     * 增加VIP的时间
     * @param id
     * @param gmtEnd
     */
    void extend(Long id, Integer position, Date gmtEnd);
    
    /**
     * 获取用户当前有效的记录
     * @param userId
     * @param position
     * @return
     */
    TopListDO getValidByUserId(Long userId, Integer position);
    
    /**
     * 获取用户最后一条记录
     * @param userId
     * @param position
     * @return
     */
    TopListDO getLatestByUserId(Long userId, Integer position);
    
    /**
     * 根据查询条件获取
     * @param queryCondition
     * @return
     */
    List<TopListDO> getByCondition(TopListQueryCondition queryCondition);
    
    /**
     * 获取分页数据
     * @param queryCondition
     * @return
     */
    List<TopListDO> getPage(TopListQueryCondition queryCondition);
    Integer getCount(TopListQueryCondition queryCondition);
}
