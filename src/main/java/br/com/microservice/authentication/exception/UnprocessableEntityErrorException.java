package br.com.microservice.authentication.exception;

import br.com.microservice.authentication.model.dto.ErrorData;

public class UnprocessableEntityErrorException extends MicroserviceAuthenticationException {
    public UnprocessableEntityErrorException(ErrorData errorData) {
        super(errorData);
    }
}
