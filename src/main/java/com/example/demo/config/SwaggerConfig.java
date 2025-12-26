package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        Server httpsServer = new Server();
        httpsServer.setUrl("https://9043.pro604cr.amypo.ai/");
        httpsServer.setDescription("HTTPS Server");
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");
        return new OpenAPI()
                .info(new Info()
                        .title("Digital Credential Verification Engine")
                        .version("1.0"))
                .servers(List.of(httpsServer))
                .addSecurityItem(
                        new SecurityRequirement().addList("Authorization")
                )
                .components(
                        new io.swagger.v3.oas.models.Components()
                                .addSecuritySchemes("Authorization", securityScheme)
                );
    }
}
