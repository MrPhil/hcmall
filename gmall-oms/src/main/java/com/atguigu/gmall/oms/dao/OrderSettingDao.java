package com.atguigu.gmall.oms.dao;

import com.atguigu.gmall.oms.entity.OrderSettingEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单配置信息
 * 
 * @author renzhonghao
 * @email teat@test.com
 * @date 2020-03-15 12:35:40
 */
@Mapper
public interface OrderSettingDao extends BaseMapper<OrderSettingEntity> {
	
}
