package br.com.microservice.authentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("br.com.microservice.authentication.model.entities")
@EnableJpaRepositories("br.com.microservice.authentication.repository")
public class MicroserviceAuthenticationApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceAuthenticationApplication.class, args);
    }

}
