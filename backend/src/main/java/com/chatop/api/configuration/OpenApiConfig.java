package com.chatop.api.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition
;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;

@Configuration
@OpenAPIDefinition(
    info = @Info(
            title = "Chatop API",
            version = "v1",
            description = "API for Chatop application"
    )
)
@SecurityScheme(
            name = "Bearer Authentication",
            scheme = "bearer",
            type = SecuritySchemeType.HTTP,
            bearerFormat = "JWT",
            in = SecuritySchemeIn.HEADER
    )
public class OpenApiConfig {
}