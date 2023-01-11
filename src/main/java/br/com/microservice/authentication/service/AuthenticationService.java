package br.com.microservice.authentication.service;

import br.com.microservice.authentication.model.dto.JwtDto;
import br.com.microservice.authentication.model.dto.LoginDto;

public interface AuthenticationService {
    JwtDto authenticateUser(LoginDto loginDto);

    JwtDto refreshUserToken(String refreshToken);

    JwtDto changePassUserToken(String username);
}
