package com.atguigu.gmall.search.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class SearchResponseAttrVO implements Serializable {

    private Long productAttributeId;//规格参数ID
    //当前属性值的所有值
    private List<String> value = new ArrayList<>();
    //属性名称
    private String name;//规格参数名称
}
