package com.atguigu.gmall.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gmall.pms.entity.SkuInfoEntity;
import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.QueryCondition;


/**
 * sku信息
 *
 * @author renzhonghao
 * @email teat@test.com
 * @date 2020-03-07 18:25:20
 */
public interface SkuInfoService extends IService<SkuInfoEntity> {

    PageVo queryPage(QueryCondition params);
}

