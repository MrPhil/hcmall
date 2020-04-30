package com.atguigu.gmall.search.pojo;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author rzhstart
 * @create 2020 - 03 - 27 - 17:29
 */

@Data
//用于搜索属性的聚过滤
public class SearchAttr {

    @Field(type = FieldType.Long)
    private Long attrId;

    @Field(type = FieldType.Keyword)
    private String attrName;

    @Field(type = FieldType.Keyword)
    private String attrValue;
}
