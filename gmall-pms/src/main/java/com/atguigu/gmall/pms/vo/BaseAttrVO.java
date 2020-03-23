package com.atguigu.gmall.pms.vo;

import com.atguigu.gmall.pms.entity.ProductAttrValueEntity;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author rzhstart
 * @create 2020 - 03 - 21 - 21:48
 */

//前端传过来valueSelected要与表列attrvalue对应，两者名字不一样重写set方法(并且传过来的是一个集合)
@Data
public class BaseAttrVO extends ProductAttrValueEntity {

    public void setValueSelected(List<String> selected){

        //如果为空啥也不用干，不为空则逗号分隔传进去
        if(CollectionUtils.isEmpty(selected)){
            return ;
        }
        this.setAttrValue(StringUtils.join(selected, ","));
        //split是把逗号分开的String分成数组集合，join是吧集合用逗号分隔化成string
    }
}
