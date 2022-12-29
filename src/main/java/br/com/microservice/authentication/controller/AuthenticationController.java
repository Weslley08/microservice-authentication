package br.com.microservice.authentication.controller;

import br.com.microservice.authentication.model.ResponseData;
import br.com.microservice.authentication.model.dto.LoginDto;
import br.com.microservice.authentication.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/microservice-authentication/v1/authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/token")
    public ResponseEntity<ResponseData> gerarTokenJwt(@RequestBody LoginDto loginDto) {
        return authenticationService.gerarTokenJwt(loginDto);
    }
}
