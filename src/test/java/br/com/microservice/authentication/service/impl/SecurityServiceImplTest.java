package br.com.microservice.authentication.service.impl;

import br.com.microservice.authentication.mapper.UserMapper;
import br.com.microservice.authentication.model.dto.UserDto;
import br.com.microservice.authentication.model.entities.UserEntity;
import br.com.microservice.authentication.model.enums.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

import static br.com.microservice.authentication.model.constants.BaseConstants.ROLE_;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SecurityServiceImplTest {

    private static final String PASSWORD_NO_HASH = "123456789";
    private final UserMapper userMapper = UserMapper.REFERENCE;
    @InjectMocks
    private SecurityServiceImpl securityService;
    @Mock
    private PasswordEncoder passwordEncoder;
    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        createEntity();
    }

    @Test
    void it_should_get_authority() {
        Set<SimpleGrantedAuthority> retorno = this.securityService.getAuthority(Role.USER);
        String role = retorno.stream().findFirst().orElseThrow().getAuthority();

        assertEquals("USER", role.replace(ROLE_, ""));
    }

    @Test
    void it_should_get_authority_start_with_ROLE_() {
        Set<SimpleGrantedAuthority> retorno = this.securityService.getAuthority(Role.USER);
        String role = retorno.stream().findFirst().orElseThrow().getAuthority();

        assertTrue(role.startsWith(ROLE_));
    }

    @Test
    void it_should_encoded_password_user() {
        this.securityService.encodedPasswordUser(userEntity, PASSWORD_NO_HASH);
        assertFalse(this.securityService.decodedPasswordUser(PASSWORD_NO_HASH, this.userEntity.getPassword()));
    }

    @Test
    void it_should_decoded_password_user() {
        String passwordHash = this.passwordEncoder.encode(PASSWORD_NO_HASH);
        assertFalse(this.securityService.decodedPasswordUser(PASSWORD_NO_HASH, passwordHash));
    }

    private void createEntity() {
        UserDto userDto = new UserDto(
                "a7ea6e8d-f08a-4033-b93c-7bbd6daa6bf9", "mock_teste_1", "123456Ab*"
        );
        this.userEntity = this.userMapper.toEntity(userDto);
    }
}