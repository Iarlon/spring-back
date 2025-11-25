package br.com.alunoonline.api.infra.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                .info(new Info()
                        .title("faculdade API")
                        .description("API Rest da aplicação de faculdade, contendo as funcionalidades de CRUD de cadastro, atualização e exclusão de alunos, professores, matérias e matrículas")
                        .contact(new Contact()
                                .name("Time Backend")
                                .email("backend@faculdade.com.br"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://faculdade/api/licenca")));
    }
}