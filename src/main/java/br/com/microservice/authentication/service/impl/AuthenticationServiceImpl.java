package br.com.microservice.authentication.service.impl;

import br.com.microservice.authentication.helper.JwtCreator;
import br.com.microservice.authentication.exception.BadRequestErrorException;
import br.com.microservice.authentication.model.ResponseData;
import br.com.microservice.authentication.model.dto.ErrorData;
import br.com.microservice.authentication.model.dto.JwtDto;
import br.com.microservice.authentication.model.dto.LoginDto;
import br.com.microservice.authentication.service.AuthenticationService;
import br.com.microservice.authentication.service.SecurityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static br.com.microservice.authentication.mapper.ModelUtilsMapper.setResponseData;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final JwtCreator jwtCreator;
    private final UserDetailsService userDetailsService;
    private final SecurityService securityService;

    public AuthenticationServiceImpl(JwtCreator jwtCreator, UserDetailsService userDetailsService, SecurityService securityService) {
        this.jwtCreator = jwtCreator;
        this.userDetailsService = userDetailsService;
        this.securityService = securityService;
    }

    @Override
    public ResponseEntity<ResponseData> gerarTokenJwt(LoginDto loginDto) {
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginDto.getUsername());

            if (!securityService.decodedPasswordUser(loginDto.getPassword(),
                    Objects.requireNonNull(userDetails).getPassword())) {
                throw new BadRequestErrorException(
                        new ErrorData(HttpStatus.BAD_REQUEST.getReasonPhrase(), "Credenciais incorretas")
                );
            }
            Map<String, String> claims = new HashMap<>();
            claims.put("username", loginDto.getUsername());

            String authorities = userDetails
                    .getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

            claims.put("authorities", authorities);
            claims.put("userId", loginDto.getUsername());

            return ResponseEntity.ok(setResponseData(new JwtDto(jwtCreator.createJwtForClaims(loginDto.getUsername(), claims))));
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>(
                    setResponseData(Collections.singletonList(new ErrorData(
                            HttpStatus.UNAUTHORIZED.getReasonPhrase(), "Credenciais não autorizadas"))),
                    HttpStatus.UNAUTHORIZED
            );
        }

    }
}
