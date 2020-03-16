package com.atguigu.gmall.oms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gmall.oms.entity.TTypeEntity;
import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.QueryCondition;


/**
 * 
 *
 * @author renzhonghao
 * @email teat@test.com
 * @date 2020-03-15 12:35:39
 */
public interface TTypeService extends IService<TTypeEntity> {

    PageVo queryPage(QueryCondition params);
}

