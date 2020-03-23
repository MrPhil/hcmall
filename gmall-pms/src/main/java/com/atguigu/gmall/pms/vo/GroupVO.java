package com.atguigu.gmall.pms.vo;

import com.atguigu.gmall.pms.entity.AttrAttrgroupRelationEntity;
import com.atguigu.gmall.pms.entity.AttrEntity;
import com.atguigu.gmall.pms.entity.AttrGroupEntity;
import lombok.Data;

import java.util.List;

/**
 * @author rzhstart
 * @create 2020 - 03 - 20 - 23:40
 */

//属性维护中查询组对应的规格参数时，返回AttrGroupEntity字段跟关系字段、属性字段。
//商品列表中添加spu返回组字段跟属性（规格参数）字段attrEntites
@Data
public class GroupVO extends AttrGroupEntity {

    private List<AttrEntity> attrEntities;

    private List<AttrAttrgroupRelationEntity> relations;
}
