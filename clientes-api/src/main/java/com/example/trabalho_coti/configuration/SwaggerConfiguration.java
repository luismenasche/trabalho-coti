package com.example.trabalho_coti.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {
    @Bean
    public OpenAPI openAPIConfig() {
        OpenAPI openAPI = new OpenAPI();

        Contact openAPIContact = new Contact();
        openAPIContact.setName("Luis Menasché");
        openAPIContact.setEmail("luis.menasche@uerj.br");

        Info openAPIInfo = new Info();
        openAPIInfo.setTitle("Clientes API");
        openAPIInfo.setDescription("API desenvolvida como trabalho final do curso da COTI");
        openAPIInfo.setVersion("v1");
        openAPIInfo.setContact(openAPIContact);

        openAPI.setInfo(openAPIInfo);

        return openAPI;
    }
}
