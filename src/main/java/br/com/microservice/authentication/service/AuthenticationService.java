package br.com.microservice.authentication.service;

import br.com.microservice.authentication.model.ResponseData;
import br.com.microservice.authentication.model.dto.LoginDto;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    ResponseEntity<ResponseData> gerarTokenJwt(LoginDto loginDto);
}
