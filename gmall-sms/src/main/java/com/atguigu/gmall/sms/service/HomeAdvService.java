package com.atguigu.gmall.sms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gmall.sms.entity.HomeAdvEntity;
import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.QueryCondition;


/**
 * 首页轮播广告
 *
 * @author renzhonghao
 * @email teat@test.com
 * @date 2020-03-09 12:13:18
 */
public interface HomeAdvService extends IService<HomeAdvEntity> {

    PageVo queryPage(QueryCondition params);
}

