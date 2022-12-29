package br.com.microservice.authentication.service;

import br.com.microservice.authentication.model.entities.UserEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;

public interface SecurityService {
    Set<SimpleGrantedAuthority> getAuthority(UserEntity user);
    void encodedPasswordUser(UserEntity user, String passwordNoHash);
    Boolean decodedPasswordUser(String originPass, String dbPass);
}