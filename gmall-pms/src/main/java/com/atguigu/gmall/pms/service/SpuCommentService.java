package com.atguigu.gmall.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gmall.pms.entity.SpuCommentEntity;
import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.QueryCondition;


/**
 * 商品评价
 *
 * @author renzhonghao
 * @email teat@test.com
 * @date 2020-03-07 18:25:20
 */
public interface SpuCommentService extends IService<SpuCommentEntity> {

    PageVo queryPage(QueryCondition params);
}

