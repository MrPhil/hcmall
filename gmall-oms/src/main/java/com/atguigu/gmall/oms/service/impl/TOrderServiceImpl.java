package com.atguigu.gmall.oms.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.Query;
import com.atguigu.core.bean.QueryCondition;

import com.atguigu.gmall.oms.dao.TOrderDao;
import com.atguigu.gmall.oms.entity.TOrderEntity;
import com.atguigu.gmall.oms.service.TOrderService;


@Service("tOrderService")
public class TOrderServiceImpl extends ServiceImpl<TOrderDao, TOrderEntity> implements TOrderService {

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<TOrderEntity> page = this.page(
                new Query<TOrderEntity>().getPage(params),
                new QueryWrapper<TOrderEntity>()
        );

        return new PageVo(page);
    }

}