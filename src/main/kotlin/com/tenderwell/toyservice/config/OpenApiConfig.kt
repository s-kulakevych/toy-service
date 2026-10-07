package com.tenderwell.toyservice.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

    @Bean
    fun toyServiceOpenApi(): OpenAPI = OpenAPI()
        .info(
            Info()
                .title("Toy Service API")
                .description("Toy web service exposing a Product REST API backed by H2")
                .version("v1"),
        )
        .components(
            Components().addSecuritySchemes(
                "basicAuth",
                SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic"),
            ),
        )
}
