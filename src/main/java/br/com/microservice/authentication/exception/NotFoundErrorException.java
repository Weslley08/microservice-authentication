package br.com.microservice.authentication.exception;

import br.com.microservice.authentication.model.dto.ErrorData;

public class NotFoundErrorException extends MicroserviceAuthenticationException {
    public NotFoundErrorException(ErrorData errorData) {
        super(errorData);
    }
}
