package br.com.microservice.authentication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

import static br.com.microservice.authentication.model.constants.RoutesConstants.*;

@Component
@EnableWebSecurity
public class WebSecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;

    public WebSecurityConfig(AuthenticationConfiguration authenticationConfiguration) {
        this.authenticationConfiguration = authenticationConfiguration;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.exceptionHandling((exceptions) ->
                exceptions
                        .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                        .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
        );

        http.authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, CREATE_USER_URL)
                .permitAll()

                .requestMatchers(HttpMethod.POST, TOKEN_USER_URL)
                .permitAll()

                .requestMatchers(HttpMethod.POST, TOKEN_RESET_PASS_USER_URL)
                .permitAll()

                .requestMatchers(HttpMethod.POST, TOKEN_REFRESH_USER_URL)
                .permitAll()

                .requestMatchers(SWAGGER_UI_URL)
                .permitAll()

                .requestMatchers(ACTUATOR_URL)
                .permitAll()

                .requestMatchers(H2_DB_URL)
                .permitAll()

                .anyRequest()
                .authenticated()
                .and()
                .httpBasic(Customizer.withDefaults())
                .addFilter(new JWTAuthorizationFilter(authenticationConfiguration.getAuthenticationManager()));
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}