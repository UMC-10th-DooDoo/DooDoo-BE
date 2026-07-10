package com.dooDoo.dooDoo.global.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI dooDooApi() {
        return new OpenAPI().info(new Info()
                .title("DooDoo API")
                .description("DooDoo 백엔드 API 문서")
                .version("v1.0"));
    }
}
