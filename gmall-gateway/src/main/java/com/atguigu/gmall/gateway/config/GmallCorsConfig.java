package com.atguigu.gmall.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

/**
 * @author rzhstart
 * @create 2020 - 03 - 09 - 18:10
 */


@Configuration
public class GmallCorsConfig {


    @Bean
    public CorsWebFilter corsWebFilter(){ //跨域过滤器，spring已经封装好的只需要调用即可

        //2.sors跨域配置对象
        CorsConfiguration configuration = new CorsConfiguration();//new一个cors配置
        //3.响应头里面告诉浏览器那些域名访问我，是否允许携带cookie，允许哪些方法,允许哪些头信息的。都要在上面new的配置类进行配置
        configuration.addAllowedOrigin("http://localhost:1000");//允许这个域名跨域访问（nodejs生成的前端工程）
        configuration.setAllowCredentials(true);//是否允许携带cookie
        configuration.addAllowedMethod("*");//允许所有的方法跨域
        configuration.addAllowedHeader("*");//允许任何的头信息

        //1.配置源对象
        UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();
        configurationSource.registerCorsConfiguration("/**",configuration);//注入配置，对上面new的进行配置，
        //返回一个cors过滤器对象                                                  // 检验跨域路径：所有；用configuration类进行配置校验
        return new CorsWebFilter(configurationSource);
    }

}

