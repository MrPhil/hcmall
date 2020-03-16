package com.atguigu.gmall.sms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gmall.sms.entity.SeckillSkuNoticeEntity;
import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.QueryCondition;


/**
 * 秒杀商品通知订阅
 *
 * @author renzhonghao
 * @email teat@test.com
 * @date 2020-03-09 12:13:18
 */
public interface SeckillSkuNoticeService extends IService<SeckillSkuNoticeEntity> {

    PageVo queryPage(QueryCondition params);
}

