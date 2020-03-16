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

import com.atguigu.gmall.oms.entity.TMenuEntity;
import com.atguigu.gmall.oms.service.TMenuService;




/**
 * 
 *
 * @author renzhonghao
 * @email teat@test.com
 * @date 2020-03-15 12:35:40
 */
@Api(tags = " 管理")
@RestController
@RequestMapping("oms/tmenu")
public class TMenuController {
    @Autowired
    private TMenuService tMenuService;

    /**
     * 列表
     */
    @ApiOperation("分页查询(排序)")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('oms:tmenu:list')")
    public Resp<PageVo> list(QueryCondition queryCondition) {
        PageVo page = tMenuService.queryPage(queryCondition);

        return Resp.ok(page);
    }


    /**
     * 信息
     */
    @ApiOperation("详情查询")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('oms:tmenu:info')")
    public Resp<TMenuEntity> info(@PathVariable("id") Integer id){
		TMenuEntity tMenu = tMenuService.getById(id);

        return Resp.ok(tMenu);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('oms:tmenu:save')")
    public Resp<Object> save(@RequestBody TMenuEntity tMenu){
		tMenuService.save(tMenu);

        return Resp.ok(null);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('oms:tmenu:update')")
    public Resp<Object> update(@RequestBody TMenuEntity tMenu){
		tMenuService.updateById(tMenu);

        return Resp.ok(null);
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('oms:tmenu:delete')")
    public Resp<Object> delete(@RequestBody Integer[] ids){
		tMenuService.removeByIds(Arrays.asList(ids));

        return Resp.ok(null);
    }

}
