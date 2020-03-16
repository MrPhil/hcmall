package com.atguigu.gmall.wms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;

/**
 * @author rzhstart
 * @create 2020 - 03 - 10 - 14:40
 */

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo());
    }


    private ApiInfo apiInfo(){
        Contact contact = new Contact("任忠豪", "", "1069025882@qq.com");
        return new ApiInfo(
                "gmall swaggerAPI文档"
                , "豪成商城"
                , "1.0"
                , "urn:tos"
                , contact
                , "Apache 2.0"
                , "http://www.apache.org/licenses/LICENSE-2.0"
                , new ArrayList());
    }


}
