package com.zhejianglab.spring3web.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * @author chenze
 * @date 2022/3/28
 */

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        //信息
        Info info = new Info()
                .title("SpringDoc 2.0.0 M1 ")
                .description("测试spring 6.x+版本使用")
                .version("v1.0.0");

        //鉴权组件
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")//固定写法
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");

        Components components = new Components()
                .addSecuritySchemes("bearer-jwt", securityScheme);

        //鉴权限制要求
        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("bearer-jwt", Arrays.asList("read", "write"));

        return new OpenAPI()
                .info(info)
                .components(components)
                .addSecurityItem(securityRequirement);
    }
}