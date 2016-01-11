package com.fans.biz.model;

import com.fans.dal.enumerate.ShoppingLevelEnum;
import com.victor.framework.common.shared.Split;
import com.victor.framework.common.tools.CollectionTools;
import com.victor.framework.common.tools.StringTools;

public class SkvTopVO {
    private ShoppingLevelEnum level;
    private Integer day;
    private Integer minute;
    
    public SkvTopVO(String config){
        if(StringTools.isEmpty(config)){
            return;
        }
        String[] split = config.split(Split.冒号);
        if(CollectionTools.isEmpty(split)){
            return;
        }
        level = ShoppingLevelEnum.getByCode(split[0]);
        try{
            day = Integer.parseInt(split[1]);
            minute = Integer.parseInt(split[2]);
        } catch(Exception ex){
            return;
        }
    }

    public ShoppingLevelEnum getLevel() {
        return level;
    }

    public Integer getDay() {
        return day;
    }

    public Integer getMinute() {
        return minute;
    }
}
