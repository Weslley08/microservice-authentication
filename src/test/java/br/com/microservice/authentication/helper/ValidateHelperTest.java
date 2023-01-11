package br.com.microservice.authentication.helper;

import br.com.microservice.authentication.exception.NotFoundErrorException;
import br.com.microservice.authentication.exception.UnauthorizedErrorException;
import br.com.microservice.authentication.exception.UnprocessableEntityErrorException;
import br.com.microservice.authentication.model.entities.UserEntity;
import br.com.microservice.authentication.model.enums.Role;
import br.com.microservice.authentication.repository.UserRepository;
import br.com.microservice.authentication.service.SecurityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Optional;

import static br.com.microservice.authentication.model.constants.ErrorMessagesConstants.userAndId;
import static br.com.microservice.authentication.model.constants.ErrorMessagesConstants.*;
import static br.com.microservice.authentication.model.constants.TasksErrorConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class ValidateHelperTest {

    @InjectMocks
    private ValidateHelper validateHelper;
    @Mock
    @SuppressWarnings("unused")
    private SecurityService securityService;
    @Mock
    @SuppressWarnings("unused")
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        UserEntity userEntity = new UserEntity(
                "a7ea6e8d-f08a-4033-b93c-7bbd6daa6bf9", "mock_teste_1", "123456789Ab",
                Role.USER, Boolean.TRUE, Boolean.TRUE
        );
        lenient().when(this.userRepository.findByUsername(userEntity.getUsername())).thenReturn(Optional.of(userEntity));
        lenient().when(this.userRepository.findById(userEntity.getUserId())).thenReturn(Optional.of(userEntity));
    }

    @Test
    void it_should_verify_password_force_entity() {
        UnprocessableEntityErrorException entityErrorException = assertThrows(
                UnprocessableEntityErrorException.class,
                () -> this.validateHelper.verifyPasswordForce("12345")
        );
        assertEquals(ERRO_VALIDACAO_CAMPOS, entityErrorException.getErrorData().getErrorTask());
    }

    @Test
    void it_should_verify_if_exists() {
        UserEntity userEntity = this.validateHelper.verifyIfExists("a7ea6e8d-f08a-4033-b93c-7bbd6daa6bf9");
        assertNotNull(userEntity);
    }

    @Test
    void it_should_verify_if_exists_not_found() {
        NotFoundErrorException entityErrorException = assertThrows(
                NotFoundErrorException.class,
                () -> this.validateHelper.verifyIfExists("")
        );
        assertEquals(USUARIO_NAO_ENCONTRADO, entityErrorException.getErrorData().getErrorTask());
    }

    @Test
    void it_should_verify_if_exists_username() {
        UserEntity userEntity = this.validateHelper.verifyIfExistsUsername("mock_teste_1");
        assertNotNull(userEntity);
    }

    @Test
    void it_should_verify_if_exists_username_not_found() {
        NotFoundErrorException entityErrorException = assertThrows(
                NotFoundErrorException.class,
                () -> this.validateHelper.verifyIfExistsUsername("")
        );
        assertEquals(USUARIO_NAO_ENCONTRADO, entityErrorException.getErrorData().getErrorTask());
    }

    @Test
    void it_should_verify_not_exists() {
        UnprocessableEntityErrorException entityErrorException = assertThrows(
                UnprocessableEntityErrorException.class,
                () -> this.validateHelper.verifyNotExists("mock_teste_1")
        );
        assertEquals(USUARIO_JA_EXISTENTE, entityErrorException.getErrorData().getErrorTask());
    }

    @Test
    void it_should_verify_role_operador() {
        UnauthorizedErrorException entityErrorException = assertThrows(
                UnauthorizedErrorException.class,
                () -> this.validateHelper.verifyRoleOperador("a7ea6e8d-f08a-4033-b93c-7bbd6daa6bf9")
        );
        assertEquals(OPERACAO_NAO_PERMITIDA, entityErrorException.getErrorData().getErrorTask());
        assertEquals(OPERADOR_NAO_AUTORIZADO, entityErrorException.getErrorData().getDetail());
    }

    @Test
    void it_should_not_allow_reset_password() {
        UnprocessableEntityErrorException entityErrorException = assertThrows(
                UnprocessableEntityErrorException.class,
                () -> this.validateHelper.notAllowResetPassword("a7ea6e8d-f08a-4033-b93c-7bbd6daa6bf9")
        );
        assertEquals(OPERACAO_NAO_PERMITIDA, entityErrorException.getErrorData().getErrorTask());
        assertEquals(ACAO_BLOQUEADA_USUARIO, entityErrorException.getErrorData().getDetail());
    }

    @Test
    void it_should_is_client_valid_user_deactivate() {
        UserDetails userDetails = new User(
                "mock_teste_1", "123456789Ab", Boolean.FALSE,
                Boolean.TRUE, Boolean.TRUE, Boolean.FALSE, new ArrayList<>()
        );

        UnauthorizedErrorException entityErrorException = assertThrows(
                UnauthorizedErrorException.class, () ->
                        this.validateHelper.isClientValid("", userDetails)
        );
        assertEquals(USUARIO_DESATIVADO, entityErrorException.getErrorData().getErrorTask());
        assertEquals(userAndId(userDetails.getUsername()), entityErrorException.getErrorData().getDetail());
    }

    @Test
    void it_should_is_client_valid_password_invalido() {
        UserDetails userDetails = new User(
                "mock_teste_1", "123456789Ab", Boolean.TRUE,
                Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, new ArrayList<>()
        );

        UnauthorizedErrorException entityErrorException = assertThrows(
                UnauthorizedErrorException.class, () ->
                        this.validateHelper.isClientValid("", userDetails)
        );
        assertEquals(OPERACAO_NAO_PERMITIDA, entityErrorException.getErrorData().getErrorTask());
        assertEquals(CREDENCIAIS_INCORRETAS, entityErrorException.getErrorData().getDetail());
    }
}