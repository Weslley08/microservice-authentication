package br.com.microservice.authentication.helper;

import br.com.microservice.authentication.exception.NotFoundErrorException;
import br.com.microservice.authentication.exception.UnauthorizedErrorException;
import br.com.microservice.authentication.exception.UnprocessableEntityErrorException;
import br.com.microservice.authentication.model.dto.CampoError;
import br.com.microservice.authentication.model.dto.ErrorData;
import br.com.microservice.authentication.model.entities.UserEntity;
import br.com.microservice.authentication.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.*;

import static br.com.microservice.authentication.model.enums.Role.ADMIN;

@Component
public class ValidateHelper {

    private final static String REGEX_PASSWORD =
            "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[$*&@#])(?:([0-9a-zA-Z$*&@#])(?!\\1)){8,}$";
    private final static List<String> STRINGS_ERROR = Arrays.stream(new String[]{
            "Deve conter 8 caracteres no mínimo", "Deve conter 1 Letra Maiúscula no mínimo",
            "Deve conter 1 Número no mínimo", "1 Símbolo no mínimo: $*&@#", "Não deve conter caracteres emse sequencia"
    }).toList();
    private final UserRepository userRepository;

    public ValidateHelper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void verifyPasswordForce(String passwordNoHash) {
        Optional.of(passwordNoHash.matches(REGEX_PASSWORD))
                .filter(Boolean::booleanValue)
                .orElseThrow(() -> {
                    List<CampoError> campoErrors = new ArrayList<>(5);

                    for (String s : STRINGS_ERROR) {
                        campoErrors.add(new CampoError("password", s));
                    }
                    throw new UnprocessableEntityErrorException(
                            new ErrorData(HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase(),
                                    "Erro na validação dos campos", campoErrors)
                    );
                });
    }

    public UserEntity verifyIfExists(String id) {
        return userRepository
                .findById(id)
                .orElseThrow(() ->
                        new NotFoundErrorException(
                                new ErrorData(
                                        HttpStatus.NOT_FOUND.getReasonPhrase(),
                                        "user: " + id + " não encontrado"
                                )
                        )
                );
    }

    public UserEntity verifyIfExistsUsername(String username) {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new NotFoundErrorException(
                                new ErrorData(
                                        HttpStatus.NOT_FOUND.getReasonPhrase(),
                                        "user: " + username + " não encontrado"
                                )
                        )
                );
    }

    public void verifyNotExists(String username) {
        userRepository
                .findByUsername(username)
                .ifPresent(user -> {
                    throw new UnprocessableEntityErrorException(
                            new ErrorData(HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase(),
                                    "Usuario: " + user.getUserId() + "já existe na base de dados")
                    );
                });
    }

    public void verifyRoleOperador(String id) {
        userRepository
                .findById(id)
                .filter(op -> Objects.equals(ADMIN, op.getRole()))
                .orElseThrow(() -> new UnauthorizedErrorException(
                        new ErrorData(
                                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                                "Operador não autorizado a mudar a role"
                        )
                ));
    }
}
