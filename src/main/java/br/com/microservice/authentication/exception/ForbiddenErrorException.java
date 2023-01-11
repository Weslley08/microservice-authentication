package br.com.microservice.authentication.exception;

import br.com.microservice.authentication.model.dto.ErrorData;

public class ForbiddenErrorException extends MicroserviceAuthenticationException {
    public ForbiddenErrorException(ErrorData errorData) {
        super(errorData);
    }
}
