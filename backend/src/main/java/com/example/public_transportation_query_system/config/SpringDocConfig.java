package com.example.public_transportation_query_system.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import com.example.public_transportation_query_system.entity.vo.Result;
import com.example.public_transportation_query_system.entity.vo.response.AuthorizeVO;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.QueryParameter;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
            .components(new Components()
                .addSecuritySchemes("apiToken", new SecurityScheme()
                    .name(HttpHeaders.AUTHORIZATION)
                    .type(SecurityScheme.Type.HTTP)
                    .in(SecurityScheme.In.HEADER)
                    .scheme("Bearer")
                    .bearerFormat("JWT")
                )
            )
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
            )
            .security(List.of(new SecurityRequirement().addList("apiToken")));
    }

    /**
     * 自定义接口
     * @return
     */
    public OpenApiCustomizer sortTagsAlphabetically() {
        return openApi -> {
            this.authorizePathItems().forEach(openApi.getPaths()::addPathItem);
        };
    }

    @Bean
    public GroupedOpenApi totalApi() {
        return GroupedOpenApi.builder()
            .group("0-总览")
            .pathsToMatch("/api/**")
            .addOpenApiCustomizer(sortTagsAlphabetically())
            .build();
    }

    @Bean
    public GroupedOpenApi accountApi() {
        return GroupedOpenApi.builder()
            .group("1-用户")
            .pathsToMatch("/api/user/**")
            .build();
    }

    @Bean
    public GroupedOpenApi RoleApi() {
        return GroupedOpenApi.builder()
            .group("2-角色")
            .pathsToMatch("/api/role/**")
            .build();
    }

    @Bean
    public GroupedOpenApi lineApi() {
        return GroupedOpenApi.builder()
            .group("3-线路")
            .pathsToMatch("/api/route/**")
            .build();
    }

    @Bean
    public GroupedOpenApi siteApi() {
        return GroupedOpenApi.builder()
            .group("4-站点")
            .pathsToMatch("/api/station/**")
            .build();
    }

    @Bean
    public GroupedOpenApi lostApi() {
        return GroupedOpenApi.builder()
            .group("5-失物招领")
            .pathsToMatch("/api/lost/**")
            .build();
    }

    @Bean
    public GroupedOpenApi noticeApi() {
        return GroupedOpenApi.builder()
            .group("6-公告")
            .pathsToMatch("/api/notice/**")
            .build();
    }

    @Bean
    public GroupedOpenApi adApi() {
        return GroupedOpenApi.builder()
            .group("7-广告")
            .pathsToMatch("/api/ad/**")
            .build();
    }

    /**
     * 手动添加登录登出接口
     * @return PathItems
     */
    private Map<String, PathItem> authorizePathItems(){
        Map<String, PathItem> map = new HashMap<>();

        map.put("/api/user/login", new PathItem()
            .post(new Operation()
                .tags(List.of("1-用户"))
                .summary("登录")
                .description("登录接口")
                .operationId("login")
                .addParametersItem(new QueryParameter()
                    .name("username")
                    .required(true)
                    .schema(new Schema<>()
                        .type("string")
                        .example("admin")
                    )
                )
                .security(new ArrayList<>())
                .addParametersItem(new QueryParameter()
                    .name("password")
                    .required(true)
                    .schema(new Schema<>()
                        .type("string")
                        .example("202cb962ac59075b964b07152d234b70")
                    )
                )
                .responses(new ApiResponses()
                    .addApiResponse("200", new ApiResponse()
                        .description("OK")
                        .content(new Content().addMediaType("*/*", new MediaType()
                            .example(Result.success("登录成功", new AuthorizeVO()))
                            .schema(new Schema<>()
                                .$ref("#/components/schemas/ResultObject")
                            )
                        ))
                    )
                )
            )
        );
        map.put("/api/user/logout", new PathItem()
            .post(new Operation()
                .tags(List.of("1-用户"))
                .summary("登出")
                .description("登出接口")
                .operationId("logout")
                .responses(new ApiResponses()
                    .addApiResponse("200", new ApiResponse()
                        .description("OK")
                        .content(new Content().addMediaType("*/*", new MediaType()
                            .example(Result.success("登出成功"))
                            .schema(new Schema<>()
                                .$ref("#/components/schemas/ResultObject")
                            )
                        ))
                    )
                )
            )

        );

        return map;
    }
}
