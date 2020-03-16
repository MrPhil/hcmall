package com.atguigu.gmall.wms.dao;

import com.atguigu.gmall.wms.entity.WareSkuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品库存
 * 
 * @author renzhonghao
 * @email teat@test.com
 * @date 2020-03-15 14:24:07
 */
@Mapper
public interface WareSkuDao extends BaseMapper<WareSkuEntity> {
	
}
