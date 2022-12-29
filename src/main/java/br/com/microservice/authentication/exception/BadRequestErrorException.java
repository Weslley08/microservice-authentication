package br.com.microservice.authentication.exception;

import br.com.microservice.authentication.model.dto.ErrorData;

public class BadRequestErrorException extends MicroserviceAuthenticationException {
    public BadRequestErrorException(ErrorData errorData) {
        super(errorData);
    }
}
