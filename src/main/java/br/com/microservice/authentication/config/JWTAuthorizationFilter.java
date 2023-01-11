package br.com.microservice.authentication.config;

import br.com.microservice.authentication.model.enums.TypeTokenEnum;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Objects;

import static br.com.microservice.authentication.helper.JwtHelper.decodedJWT;
import static br.com.microservice.authentication.model.constants.SecurityConstants.*;
import static br.com.microservice.authentication.model.enums.TypeTokenEnum.ACCESS_TOKEN;
import static br.com.microservice.authentication.model.enums.TypeTokenEnum.RESET_PASSWORD_TOKEN;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    public JWTAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {

        String accessToken = req.getHeader(HEADER_ACCESS_TOKEN_STRING);
        String resetPassToken = req.getHeader(HEADER_RESET_PASSWORD_TOKEN_STRING);

        if (Objects.nonNull(resetPassToken)) {
            System.setProperty(String.valueOf(RESET_PASSWORD_TOKEN), resetPassToken.replace(TOKEN_PREFIX, ""));
            authenticationToken(resetPassToken, RESET_PASSWORD_TOKEN);
            chain.doFilter(req, res);
            return;
        } else if (Objects.isNull(accessToken) || !accessToken.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }
        System.setProperty(String.valueOf(ACCESS_TOKEN), accessToken.replace(TOKEN_PREFIX, ""));
        authenticationToken(accessToken, ACCESS_TOKEN);
        chain.doFilter(req, res);
    }

    private void authenticationToken(String token, TypeTokenEnum tokenEnum) {
        DecodedJWT decodedJWT = decodedJWT(token, tokenEnum);
        String user = decodedJWT.getSubject();
        String authorities = decodedJWT.getClaim(AUTHORITIES).asString();

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                user, null,
                Collections.singletonList(new SimpleGrantedAuthority(ROLE_ + authorities))
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}