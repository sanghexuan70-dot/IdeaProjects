package com.learn.hello_api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean    // 又见 @Bean：OpenAPI 是第三方库的类，没法给它加 @Component，只能手动装配
    public OpenAPI openAPI() {
        return new OpenAPI().info(new Info()
                .title("hello-api 接口文档")
                .description("全栈学习第一个服务的 API")
                .version("v1.0.0"));
    }
}