package com.atguigu.gmall.oms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gmall.oms.entity.OrderReturnReasonEntity;
import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.QueryCondition;


/**
 * 退货原因
 *
 * @author renzhonghao
 * @email teat@test.com
 * @date 2020-03-15 12:35:40
 */
public interface OrderReturnReasonService extends IService<OrderReturnReasonEntity> {

    PageVo queryPage(QueryCondition params);
}

