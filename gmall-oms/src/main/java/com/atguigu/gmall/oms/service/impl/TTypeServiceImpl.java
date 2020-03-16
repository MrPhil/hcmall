package com.atguigu.gmall.oms.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.Query;
import com.atguigu.core.bean.QueryCondition;

import com.atguigu.gmall.oms.dao.TTypeDao;
import com.atguigu.gmall.oms.entity.TTypeEntity;
import com.atguigu.gmall.oms.service.TTypeService;


@Service("tTypeService")
public class TTypeServiceImpl extends ServiceImpl<TTypeDao, TTypeEntity> implements TTypeService {

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<TTypeEntity> page = this.page(
                new Query<TTypeEntity>().getPage(params),
                new QueryWrapper<TTypeEntity>()
        );

        return new PageVo(page);
    }

}