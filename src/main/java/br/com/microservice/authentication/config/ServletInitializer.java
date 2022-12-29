package br.com.microservice.authentication.config;

import br.com.microservice.authentication.MicroserviceAuthenticationApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(MicroserviceAuthenticationApplication.class);
	}

}
