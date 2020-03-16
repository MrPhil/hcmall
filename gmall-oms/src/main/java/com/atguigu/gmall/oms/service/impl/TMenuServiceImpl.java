package com.atguigu.gmall.oms.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.Query;
import com.atguigu.core.bean.QueryCondition;

import com.atguigu.gmall.oms.dao.TMenuDao;
import com.atguigu.gmall.oms.entity.TMenuEntity;
import com.atguigu.gmall.oms.service.TMenuService;


@Service("tMenuService")
public class TMenuServiceImpl extends ServiceImpl<TMenuDao, TMenuEntity> implements TMenuService {

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<TMenuEntity> page = this.page(
                new Query<TMenuEntity>().getPage(params),
                new QueryWrapper<TMenuEntity>()
        );

        return new PageVo(page);
    }

}