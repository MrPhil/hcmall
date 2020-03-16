package com.atguigu.gmall.oms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gmall.oms.entity.OrderReturnApplyEntity;
import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.QueryCondition;


/**
 * 订单退货申请
 *
 * @author renzhonghao
 * @email teat@test.com
 * @date 2020-03-15 12:35:40
 */
public interface OrderReturnApplyService extends IService<OrderReturnApplyEntity> {

    PageVo queryPage(QueryCondition params);
}

