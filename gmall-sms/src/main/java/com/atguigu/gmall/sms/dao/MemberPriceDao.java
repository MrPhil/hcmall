package com.atguigu.gmall.sms.dao;

import com.atguigu.gmall.sms.entity.MemberPriceEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品会员价格
 * 
 * @author renzhonghao
 * @email teat@test.com
 * @date 2020-03-09 12:13:18
 */
@Mapper
public interface MemberPriceDao extends BaseMapper<MemberPriceEntity> {
	
}
