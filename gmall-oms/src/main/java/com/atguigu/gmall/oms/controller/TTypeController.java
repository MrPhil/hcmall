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

import com.atguigu.gmall.oms.entity.TTypeEntity;
import com.atguigu.gmall.oms.service.TTypeService;




/**
 * 
 *
 * @author renzhonghao
 * @email teat@test.com
 * @date 2020-03-15 12:35:39
 */
@Api(tags = " 管理")
@RestController
@RequestMapping("oms/ttype")
public class TTypeController {
    @Autowired
    private TTypeService tTypeService;

    /**
     * 列表
     */
    @ApiOperation("分页查询(排序)")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('oms:ttype:list')")
    public Resp<PageVo> list(QueryCondition queryCondition) {
        PageVo page = tTypeService.queryPage(queryCondition);

        return Resp.ok(page);
    }


    /**
     * 信息
     */
    @ApiOperation("详情查询")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('oms:ttype:info')")
    public Resp<TTypeEntity> info(@PathVariable("id") Integer id){
		TTypeEntity tType = tTypeService.getById(id);

        return Resp.ok(tType);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('oms:ttype:save')")
    public Resp<Object> save(@RequestBody TTypeEntity tType){
		tTypeService.save(tType);

        return Resp.ok(null);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('oms:ttype:update')")
    public Resp<Object> update(@RequestBody TTypeEntity tType){
		tTypeService.updateById(tType);

        return Resp.ok(null);
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('oms:ttype:delete')")
    public Resp<Object> delete(@RequestBody Integer[] ids){
		tTypeService.removeByIds(Arrays.asList(ids));

        return Resp.ok(null);
    }

}
