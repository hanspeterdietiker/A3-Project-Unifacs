package com.restaurant.a3.RestaurantApi.configs;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocOpenApiConfig {

    @Bean
    public OpenAPI openApi(){
        return new OpenAPI().components(
                        new Components().addSecuritySchemes("security", securityScheme())
                )
                .info(
                        new Info()
                                .title("API RESTAURANT - Spring")
                                .description("O sistema de gerenciamento de restaurantes é uma aplicação web\n" +
                                        "que permite aos usuários, dependendo da sua autenticação,\n" +
                                        "realizar operações de CRUD (Create, Read, Update, Delete) em\n" +
                                        "restaurantes, bem como avaliá-los e deixar comentários.")
                                .version("v1.0")
                                .license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0"))
                                .contact(new Contact().name("Gabriel Silva Magalhães").email("gabiles278@gmail.com"))
                );
    }

    private SecurityScheme securityScheme() {
        return new SecurityScheme()
                .description("Insira um bearer token valido para prosseguir")
                .type(SecurityScheme.Type.HTTP)
                .in(SecurityScheme.In.HEADER)
                .scheme("bearer")
                .bearerFormat("JWT")
                .name("security");
    }
}
