package br.com.microservice.authentication.helper;

import br.com.microservice.authentication.exception.NotFoundErrorException;
import br.com.microservice.authentication.exception.UnauthorizedErrorException;
import br.com.microservice.authentication.exception.UnprocessableEntityErrorException;
import br.com.microservice.authentication.model.dto.CampoError;
import br.com.microservice.authentication.model.dto.ErrorData;
import br.com.microservice.authentication.model.entities.UserEntity;
import br.com.microservice.authentication.repository.UserRepository;
import br.com.microservice.authentication.service.SecurityService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static br.com.microservice.authentication.model.constants.BaseConstants.PASSWORD;
import static br.com.microservice.authentication.model.constants.ErrorMessagesConstants.*;
import static br.com.microservice.authentication.model.constants.RegexConstants.REGEX_PASSWORD;
import static br.com.microservice.authentication.model.constants.TasksErrorConstants.userAndId;
import static br.com.microservice.authentication.model.constants.TasksErrorConstants.*;
import static br.com.microservice.authentication.model.enums.RoleEnum.ADMIN;

@Component
public class ValidateHelper {

    private final UserRepository userRepository;
    private final SecurityService securityService;

    public ValidateHelper(UserRepository userRepository,
                          SecurityService securityService) {
        this.userRepository = userRepository;
        this.securityService = securityService;
    }

    public void verifyPasswordForce(String passwordNoHash) {
        Optional.of(passwordNoHash.matches(REGEX_PASSWORD))
                .filter(Boolean::booleanValue)
                .orElseThrow(() -> {
                    List<CampoError> campoErrors = new ArrayList<>(STRINGS_ERRORS_PASSWORD.length);
                    for (String campos : STRINGS_ERRORS_PASSWORD) campoErrors.add(new CampoError(PASSWORD, campos));
                    throw new UnprocessableEntityErrorException(
                            new ErrorData(ERRO_VALIDACAO_CAMPOS, campoErrors)
                    );
                });
    }

    public UserEntity verifyIfExists(String id) {
        return userRepository
                .findById(id)
                .orElseThrow(() ->
                        new NotFoundErrorException(new ErrorData(USUARIO_NAO_ENCONTRADO))
                );
    }

    public UserEntity verifyIfExistsUsername(String username) {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new NotFoundErrorException(new ErrorData(USUARIO_NAO_ENCONTRADO))
                );
    }

    public void verifyNotExists(String username) {
        userRepository
                .findByUsername(username)
                .ifPresent(user -> {
                    throw new UnprocessableEntityErrorException(new ErrorData(USUARIO_JA_EXISTENTE));
                });
    }

    public void verifyRoleOperador(String id) {
        userRepository
                .findById(id)
                .filter(op -> Objects.equals(ADMIN, op.getRole()))
                .orElseThrow(() -> new UnauthorizedErrorException(
                        new ErrorData(OPERACAO_NAO_PERMITIDA, OPERADOR_NAO_AUTORIZADO)
                ));
    }

    public void notAllowResetPassword(String id) {
        userRepository
                .findById(id)
                .filter(UserEntity::getNotResetPassword)
                .ifPresent(user -> {
                    throw new UnprocessableEntityErrorException(new ErrorData(
                            OPERACAO_NAO_PERMITIDA, ACAO_BLOQUEADA_USUARIO
                    ));
                });
    }

    public void isClientValid(String password, UserDetails userDetails) {
        if (!userDetails.isEnabled() || !userDetails.isAccountNonLocked()) {
            throw new UnauthorizedErrorException(
                    new ErrorData(USUARIO_DESATIVADO, userAndId(userDetails.getUsername()))
            );
        }
        if (!securityService.decodedPasswordUser(password, userDetails.getPassword())) {
            throw new UnauthorizedErrorException(
                    new ErrorData(OPERACAO_NAO_PERMITIDA, CREDENCIAIS_INCORRETAS)
            );
        }
    }

    public void isClientValid(UserDetails userDetails) {
        if (!userDetails.isEnabled() || !userDetails.isAccountNonLocked()) {
            throw new UnauthorizedErrorException(
                    new ErrorData(USUARIO_DESATIVADO, userAndId(userDetails.getUsername()))
            );
        }
    }
}
