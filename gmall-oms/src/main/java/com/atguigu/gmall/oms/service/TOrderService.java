package com.atguigu.gmall.oms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gmall.oms.entity.TOrderEntity;
import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.QueryCondition;


/**
 * 
 *
 * @author renzhonghao
 * @email teat@test.com
 * @date 2020-03-15 12:35:40
 */
public interface TOrderService extends IService<TOrderEntity> {

    PageVo queryPage(QueryCondition params);
}

