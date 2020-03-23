package com.atguigu.gmall.pms.vo;

import com.atguigu.gmall.pms.entity.SkuInfoEntity;
import com.atguigu.gmall.pms.entity.SkuSaleAttrValueEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author rzhstart
 * @create 2020 - 03 - 21 - 22:23
 */

//sku属性扩展营销信息
@Data
public class SkuInfoVO extends SkuInfoEntity {

    //积分营销相关字段SkuBoundsEntity sms
    private BigDecimal growBounds;
    private BigDecimal buyBounds;
    private List<Integer> work;

    //打折相关字段SkuLadderEntity sms
    private Integer fullCount;
    private BigDecimal discount;
    private Integer ladderAddOther;//addOther


    //满减相关字段SkuFullRedutionEntity sms
    private BigDecimal fullPrice;
    private BigDecimal reducePrice;
    private Integer fullAddOther;//addOther

    //sku销售属性及值sku_sale_attr_value pms
    private List<SkuSaleAttrValueEntity> saleAttrs;

    //skuInfo的skuDefaultImg与前端传过来的名字不一样
    private List<String> images;



}
