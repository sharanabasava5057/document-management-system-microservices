package com.example.metadata.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Document Metadata Service API",
                version = "1.0",
                description = "CRUD operations for document metadata in the DMS project"
        )
)
public class OpenApiConfig {
}
