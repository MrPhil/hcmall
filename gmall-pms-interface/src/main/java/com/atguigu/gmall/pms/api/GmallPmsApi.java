package com.atguigu.gmall.pms.api;

import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.QueryCondition;
import com.atguigu.core.bean.Resp;
import com.atguigu.gmall.pms.entity.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author rzhstart
 * @create 2020 - 03 - 27 - 19:37
 */
public interface GmallPmsApi {

    @PostMapping("/pms/spuinfo/page")
    Resp<List<SpuInfoEntity>> querySpusByPage(@RequestBody QueryCondition queryCondition);

    @GetMapping("/pms/skuinfo/{spuId}")
    Resp<List<SkuInfoEntity>> querySkusBySpuId(@PathVariable("spuId")Long spuId);

    @GetMapping("/pms/brand/info/{brandId}")
    Resp<BrandEntity> queryBrandById(@PathVariable("brandId") Long brandId);

    @GetMapping("/pms/category/info/{catId}")
    Resp<CategoryEntity> queryCategoryById(@PathVariable("catId") Long catId);

    @GetMapping("/pms/productattrvalue/{spuId}")
    Resp<List<ProductAttrValueEntity>> querySearchValueBySpuId(@PathVariable("spuId")Long spuId);

    }
