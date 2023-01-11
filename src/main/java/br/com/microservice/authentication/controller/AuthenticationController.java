package br.com.microservice.authentication.controller;

import br.com.microservice.authentication.model.ResponseEntityCustom;
import br.com.microservice.authentication.model.dto.JwtDto;
import br.com.microservice.authentication.model.dto.LoginDto;
import br.com.microservice.authentication.service.AuthenticationService;
import io.micrometer.core.annotation.Timed;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static br.com.microservice.authentication.model.constants.RoutesConstants.*;
import static br.com.microservice.authentication.model.constants.RoutesConstants.TOKENS_AND_CHANGE_PASS_PATH;

@RestController
@Timed(histogram = true, value = "auth")
@RequestMapping(value = BASE_PATH_AND_V1, produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping(value = TOKENS_PATH,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JwtDto> authenticateUser(@RequestBody LoginDto loginDto) {
        return ResponseEntityCustom.created(authenticationService.authenticateUser(loginDto));
    }

    @PostMapping(value = TOKENS_AND_REFRESH_PATH)
    public ResponseEntity<JwtDto> refreshUserToken(@RequestHeader("refresh_token") String refreshToken) {
        return ResponseEntityCustom.created(authenticationService.refreshUserToken(refreshToken));
    }

    @PostMapping(value = TOKENS_AND_CHANGE_PASS_PATH)
    public ResponseEntity<JwtDto> changePassUserToken(@PathVariable String username) {
        return ResponseEntityCustom.created(authenticationService.changePassUserToken(username));
    }
}
