package br.com.microservice.authentication.exception;

import br.com.microservice.authentication.model.dto.ErrorData;

public class InternalServerErrorException extends MicroserviceAuthenticationException {
    public InternalServerErrorException(ErrorData errorData) {
        super(errorData);
    }
}
