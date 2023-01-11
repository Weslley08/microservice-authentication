package br.com.microservice.authentication.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SpringDocConfig {


    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(metaData());
    }

    @Bean
    public GroupedOpenApi httpApi() {
        return GroupedOpenApi
                .builder()
                .group("auth")
                .pathsToMatch("/**")
                .build();
    }

    private Info metaData() {
        return new Info()
                .title("Microserviço de autenticação de usuário")
                .description("Este microserviço tem como função autenticar e autorizar outros serviços")
                .version("1.0.0")
                .license(license())
                .contact(createContact());
    }

    private License license() {
        License license = new License();
        license.setName("Apache License Version 2.0");
        license.setUrl("https://www.apache.org/licenses/LICENSE-2.0");
        return license;
    }

    private Contact createContact() {
        Contact contact = new Contact();
        contact.setName("Weslley Jonathan C. Santana");
        contact.setUrl("https://github.com/Weslley08");
        contact.setEmail("weslleyjonathas88@gmail.com");
        return contact;
    }
}
