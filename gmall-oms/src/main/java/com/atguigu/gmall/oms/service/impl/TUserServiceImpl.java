package com.atguigu.gmall.oms.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.Query;
import com.atguigu.core.bean.QueryCondition;

import com.atguigu.gmall.oms.dao.TUserDao;
import com.atguigu.gmall.oms.entity.TUserEntity;
import com.atguigu.gmall.oms.service.TUserService;


@Service("tUserService")
public class TUserServiceImpl extends ServiceImpl<TUserDao, TUserEntity> implements TUserService {

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<TUserEntity> page = this.page(
                new Query<TUserEntity>().getPage(params),
                new QueryWrapper<TUserEntity>()
        );

        return new PageVo(page);
    }

}