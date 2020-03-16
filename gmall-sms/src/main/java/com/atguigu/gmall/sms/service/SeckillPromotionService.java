package com.atguigu.gmall.sms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gmall.sms.entity.SeckillPromotionEntity;
import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.QueryCondition;


/**
 * 秒杀活动
 *
 * @author renzhonghao
 * @email teat@test.com
 * @date 2020-03-09 12:13:18
 */
public interface SeckillPromotionService extends IService<SeckillPromotionEntity> {

    PageVo queryPage(QueryCondition params);
}

