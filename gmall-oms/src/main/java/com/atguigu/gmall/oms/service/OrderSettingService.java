package com.atguigu.gmall.oms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gmall.oms.entity.OrderSettingEntity;
import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.QueryCondition;


/**
 * 订单配置信息
 *
 * @author renzhonghao
 * @email teat@test.com
 * @date 2020-03-15 12:35:40
 */
public interface OrderSettingService extends IService<OrderSettingEntity> {

    PageVo queryPage(QueryCondition params);
}

