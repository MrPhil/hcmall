package com.atguigu.gmall.search.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author rzhstart
 * @create 2020 - 03 - 29 - 11:47
 */

@Configuration
public class ElasticsearchConfig {
    //由于使用高亮，必须配置高级客户端
    @Bean
    public RestHighLevelClient restHighLevelClient(){
        return new RestHighLevelClient(RestClient.builder(HttpHost.create("192.168.0.108:9200")));//创建集群节点
    }

}
