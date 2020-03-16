package com.atguigu.gmall.oms.controller;

import java.util.Arrays;
import java.util.Map;


import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.QueryCondition;
import com.atguigu.core.bean.Resp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.atguigu.gmall.oms.entity.TUserEntity;
import com.atguigu.gmall.oms.service.TUserService;




/**
 * 
 *
 * @author renzhonghao
 * @email teat@test.com
 * @date 2020-03-15 12:35:39
 */
@Api(tags = " 管理")
@RestController
@RequestMapping("oms/tuser")
public class TUserController {
    @Autowired
    private TUserService tUserService;

    /**
     * 列表
     */
    @ApiOperation("分页查询(排序)")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('oms:tuser:list')")
    public Resp<PageVo> list(QueryCondition queryCondition) {
        PageVo page = tUserService.queryPage(queryCondition);

        return Resp.ok(page);
    }


    /**
     * 信息
     */
    @ApiOperation("详情查询")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('oms:tuser:info')")
    public Resp<TUserEntity> info(@PathVariable("id") Integer id){
		TUserEntity tUser = tUserService.getById(id);

        return Resp.ok(tUser);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('oms:tuser:save')")
    public Resp<Object> save(@RequestBody TUserEntity tUser){
		tUserService.save(tUser);

        return Resp.ok(null);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('oms:tuser:update')")
    public Resp<Object> update(@RequestBody TUserEntity tUser){
		tUserService.updateById(tUser);

        return Resp.ok(null);
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('oms:tuser:delete')")
    public Resp<Object> delete(@RequestBody Integer[] ids){
		tUserService.removeByIds(Arrays.asList(ids));

        return Resp.ok(null);
    }

}
