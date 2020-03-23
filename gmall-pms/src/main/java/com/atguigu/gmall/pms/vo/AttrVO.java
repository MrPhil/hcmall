package com.atguigu.gmall.pms.vo;

import com.atguigu.gmall.pms.entity.AttrEntity;
import lombok.Data;

/**
 * @author rzhstart
 * @create 2020 - 03 - 21 - 13:08
 */

@Data
//属性维护中添加属性的时候修改属性表还要修改关系表，返回过来的组id不在AttrEntity字段中，故扩展
public class AttrVO extends AttrEntity {

    private Long attrGroupId;
}
