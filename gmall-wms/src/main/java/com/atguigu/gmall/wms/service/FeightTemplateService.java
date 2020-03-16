package com.atguigu.gmall.wms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gmall.wms.entity.FeightTemplateEntity;
import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.QueryCondition;


/**
 * 运费模板
 *
 * @author renzhonghao
 * @email teat@test.com
 * @date 2020-03-15 14:24:07
 */
public interface FeightTemplateService extends IService<FeightTemplateEntity> {

    PageVo queryPage(QueryCondition params);
}

