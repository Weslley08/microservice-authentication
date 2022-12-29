package br.com.microservice.authentication.service.impl;

import br.com.microservice.authentication.model.entities.UserEntity;
import br.com.microservice.authentication.service.SecurityService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SecurityServiceImpl implements SecurityService {

    private final PasswordEncoder passwordEncoder;

    public SecurityServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Set<SimpleGrantedAuthority> getAuthority(UserEntity user) {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
    }

    @Override
    public void encodedPasswordUser(UserEntity user, String passwordNoHash) {
        String passwordHash = passwordEncoder.encode(passwordNoHash);
        user.setPassword(passwordHash);
    }

    @Override
    public Boolean decodedPasswordUser(String originPass, String dbPass) {
        return passwordEncoder.matches(originPass, dbPass);
    }
}
