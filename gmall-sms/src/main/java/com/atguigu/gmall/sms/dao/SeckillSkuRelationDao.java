package com.atguigu.gmall.sms.dao;

import com.atguigu.gmall.sms.entity.SeckillSkuRelationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 秒杀活动商品关联
 * 
 * @author renzhonghao
 * @email teat@test.com
 * @date 2020-03-09 12:13:18
 */
@Mapper
public interface SeckillSkuRelationDao extends BaseMapper<SeckillSkuRelationEntity> {
	
}
