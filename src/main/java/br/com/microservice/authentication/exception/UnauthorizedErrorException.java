package br.com.microservice.authentication.exception;

import br.com.microservice.authentication.model.dto.ErrorData;

public class UnauthorizedErrorException extends MicroserviceAuthenticationException {
    public UnauthorizedErrorException(ErrorData errorData) {
        super(errorData);
    }
}
