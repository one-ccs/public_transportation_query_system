package com.example.publictransportationquerysystem.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("重庆公交管理系统 API")
                .description("重庆公共交通管理系统应用程序开放文档")
                .version("v1.0.0")
                .license(new License()
                    .name("开源协议：Apache-v2")
                    .url("http://www.apache.org/licenses/LICENSE-2.0.html")
                )
                .contact(new Contact()
                    .name("one-ccs@foxmail.com")
                    .email("one-ccs@foxmail.com")
                )
            )
            .externalDocs(new ExternalDocumentation()
                .description("博客：one-ccs")
                .url("https://blog.csdn.net/qq_43155814")
            );
    }

    @Bean
    public GroupedOpenApi totalApi() {
        return GroupedOpenApi.builder()
            .group("1-总览")
            .pathsToMatch("/api")
            .build();
    }

    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder()
            .group("2-用户")
            .pathsToMatch("/api/user")
            .build();
    }

    @Bean
    public GroupedOpenApi lineApi() {
        return GroupedOpenApi.builder()
            .group("3-线路")
            .pathsToMatch("/api/line")
            .build();
    }

    @Bean
    public GroupedOpenApi siteApi() {
        return GroupedOpenApi.builder()
            .group("4-站点")
            .pathsToMatch("/api/site")
            .build();
    }

    @Bean
    public GroupedOpenApi lostApi() {
        return GroupedOpenApi.builder()
            .group("5-失物招领")
            .pathsToMatch("/api/lost")
            .build();
    }

    @Bean
    public GroupedOpenApi noticeApi() {
        return GroupedOpenApi.builder()
            .group("6-公告")
            .pathsToMatch("/api/notice")
            .build();
    }
}
