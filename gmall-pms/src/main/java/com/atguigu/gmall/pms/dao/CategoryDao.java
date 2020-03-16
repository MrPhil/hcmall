package com.atguigu.gmall.pms.dao;

import com.atguigu.gmall.pms.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author renzhonghao
 * @email teat@test.com
 * @date 2020-03-07 18:25:20
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
