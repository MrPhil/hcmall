package com.atguigu.gmall.sms.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author rzhstart
 * @create 2020 - 03 - 22 - 14:45
 */

//新建spu时pms传递销售信息的数据模型
@Data
public class SkuSaleVO {

    private Long skuId;

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
}
