package com.zhaoli.pay.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
* Swagger2 相关配置
*/
@Configuration
@EnableSwagger2
@EnableKnife4j
public class Knife4jConfig {
    
    /**
    * 创建api
    */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
            .useDefaultResponseMessages(false)
            .apiInfo(apiInfo())
            .select()
            // api接口扫包范围
            .apis(RequestHandlerSelectors.basePackage("com.zhaoli.pay.controller"))
            .paths(PathSelectors.any())
            .build();
        
    }
    
    /**
    * 配置api信息
    */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .description("zhaoli-pay-parent系统Api接口文档")
            .contact(new Contact("zhaoli", "https://www.zhaoli.com", "333333@qq.com"))
            .version("v1.1.0")
            .title("API测试文档")
            .build();
    }
    
}