package br.com.microservice.authentication.service.impl;

import br.com.microservice.authentication.helper.ValidateHelper;
import br.com.microservice.authentication.model.UserDetailsCustom;
import br.com.microservice.authentication.model.dto.JwtDto;
import br.com.microservice.authentication.model.dto.LoginDto;
import br.com.microservice.authentication.service.AuthenticationService;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static br.com.microservice.authentication.helper.JwtHelper.createJwtForClaims;
import static br.com.microservice.authentication.helper.JwtHelper.decodedJWT;
import static br.com.microservice.authentication.model.constants.BaseConstants.AUTHORITIES;
import static br.com.microservice.authentication.model.constants.BaseConstants.USERNAME;
import static br.com.microservice.authentication.model.enums.TypeTokenEnum.*;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final ValidateHelper validateHelper;
    private final UserDetailsService userDetailsService;

    public AuthenticationServiceImpl(ValidateHelper validateHelper,
                                     UserDetailsService userDetailsService) {
        this.validateHelper = validateHelper;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public JwtDto authenticateUser(LoginDto loginDto) {
        Map<String, String> claims = new HashMap<>();
        UserDetailsCustom userDetails =
                (UserDetailsCustom) userDetailsService.loadUserByUsername(loginDto.getUsername());
        validateHelper.isClientValid(loginDto.getPassword(), userDetails);

        claims.put(USERNAME, loginDto.getUsername());
        claims.put(AUTHORITIES, getAuthorities(userDetails));

        return createJwtResponse(userDetails.getUserId(), claims);
    }

    @Override
    public JwtDto refreshUserToken(String refreshToken) {
        Map<String, String> claims = new HashMap<>();
        DecodedJWT decodedJWT = decodedJWT(refreshToken, REFRESH_TOKEN);

        String userId = decodedJWT.getSubject();
        String username = decodedJWT.getClaim(USERNAME).asString();
        String authorities = decodedJWT.getClaim(AUTHORITIES).asString();

        claims.put(USERNAME, username);
        claims.put(AUTHORITIES, authorities);

        return createJwtResponse(userId, claims);
    }

    @Override
    public JwtDto changePassUserToken(String username) {
        Map<String, String> claims = new HashMap<>();
        UserDetailsCustom userDetails =
                (UserDetailsCustom) userDetailsService.loadUserByUsername(username);
        validateHelper.isClientValid(userDetails);

        claims.put(USERNAME, userDetails.getUsername());
        claims.put(AUTHORITIES, getAuthorities(userDetails));

        return new JwtDto(
                createJwtForClaims(userDetails.getUserId(), claims, RESET_PASSWORD_TOKEN)
        );
    }

    private JwtDto createJwtResponse(String userId, Map<String, String> claims) {
        return new JwtDto(
                createJwtForClaims(userId, claims, ACCESS_TOKEN),
                createJwtForClaims(userId, claims, REFRESH_TOKEN)
        );
    }

    private String getAuthorities(UserDetailsCustom userDetails) {
        return userDetails
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
    }
}
