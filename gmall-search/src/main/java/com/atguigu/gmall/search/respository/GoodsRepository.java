package com.atguigu.gmall.search.respository;

import com.atguigu.gmall.search.pojo.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author rzhstart
 * @create 2020 - 03 - 28 - 11:26
 */

//无法获取高亮，得用ElasticsearchConfig
                                                                //操作类型，id类型
public interface GoodsRepository extends ElasticsearchRepository<Goods, Long> {
}
