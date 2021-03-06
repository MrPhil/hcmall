package com.atguigu.gmall.oms.dao;

import com.atguigu.gmall.oms.entity.OrderItemEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单项信息
 * 
 * @author renzhonghao
 * @email teat@test.com
 * @date 2020-03-15 12:35:40
 */
@Mapper
public interface OrderItemDao extends BaseMapper<OrderItemEntity> {
	
}
