package com.key.task.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(info = @Info(title = "Key-App API", version = "v1", description = "API documentation for the Key-App application"))
@Configuration
public class OpenApiConfig {
}
