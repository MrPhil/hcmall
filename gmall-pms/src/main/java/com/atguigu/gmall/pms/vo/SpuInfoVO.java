package com.atguigu.gmall.pms.vo;

import com.atguigu.gmall.pms.entity.SpuInfoEntity;
import lombok.Data;

import java.security.PrivateKey;
import java.util.List;

/**
 * @author rzhstart
 * @create 2020 - 03 - 21 - 21:38
 */


//新建spu保存（属性信息基本信息相关信息的返回对象模型）
@Data
public class SpuInfoVO extends SpuInfoEntity {

    private List<String> spuImages;

    private List<BaseAttrVO> baseAttrs;

    private List<SkuInfoVO> skus;
}
