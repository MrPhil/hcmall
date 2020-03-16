package com.atguigu.gmall.oms.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.Query;
import com.atguigu.core.bean.QueryCondition;

import com.atguigu.gmall.oms.dao.TAdminDao;
import com.atguigu.gmall.oms.entity.TAdminEntity;
import com.atguigu.gmall.oms.service.TAdminService;


@Service("tAdminService")
public class TAdminServiceImpl extends ServiceImpl<TAdminDao, TAdminEntity> implements TAdminService {

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<TAdminEntity> page = this.page(
                new Query<TAdminEntity>().getPage(params),
                new QueryWrapper<TAdminEntity>()
        );

        return new PageVo(page);
    }

}