package com.atguigu.gmall.ums.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gmall.ums.entity.MemberEntity;
import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.QueryCondition;


/**
 * 会员
 *
 * @author renzhonghao
 * @email teat@test.com
 * @date 2020-03-15 13:18:04
 */
public interface MemberService extends IService<MemberEntity> {

    PageVo queryPage(QueryCondition params);
}

