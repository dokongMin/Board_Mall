package com.dokong.board.web.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "게시판_쇼핑몰 API 명세서",
        description = "게시판_쇼핑몰 토이프로젝트 API 명세서",
        version = "v1"))
@Configuration
public class OpenApiConfig {

    @Bean
    public GroupedOpenApi chatOpenApi() {
        String[] paths = {"/users/**", "/board/**", "/items/**", "/category/**" , "/admin/**", "/rest/**", "/order/**"};

        return GroupedOpenApi.builder()
                .group("게시판_쇼핑몰 API v1")
                .pathsToMatch(paths)
                .build();
    }
}
