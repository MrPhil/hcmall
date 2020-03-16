package com.atguigu.gmall.sms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gmall.sms.entity.SpuLadderEntity;
import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.QueryCondition;


/**
 * 商品阶梯价格
 *
 * @author renzhonghao
 * @email teat@test.com
 * @date 2020-03-09 12:13:18
 */
public interface SpuLadderService extends IService<SpuLadderEntity> {

    PageVo queryPage(QueryCondition params);
}

