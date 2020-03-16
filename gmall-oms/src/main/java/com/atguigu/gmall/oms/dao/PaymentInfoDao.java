package com.atguigu.gmall.oms.dao;

import com.atguigu.gmall.oms.entity.PaymentInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 支付信息表
 * 
 * @author renzhonghao
 * @email teat@test.com
 * @date 2020-03-15 12:35:40
 */
@Mapper
public interface PaymentInfoDao extends BaseMapper<PaymentInfoEntity> {
	
}
