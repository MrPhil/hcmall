package com.atguigu.gmall.ums.dao;

import com.atguigu.gmall.ums.entity.MemberLoginLogEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员登录记录
 * 
 * @author renzhonghao
 * @email teat@test.com
 * @date 2020-03-15 13:18:04
 */
@Mapper
public interface MemberLoginLogDao extends BaseMapper<MemberLoginLogEntity> {
	
}
