package br.com.microservice.authentication.exception;

import br.com.microservice.authentication.model.dto.ErrorData;
import jakarta.validation.constraints.NotNull;

public class MicroserviceAuthenticationException extends RuntimeException {

    private final ErrorData errorData;

    public MicroserviceAuthenticationException(@NotNull ErrorData errorData) {
        super("Authentication Exception: " + errorData.toString());
        this.errorData = errorData;
    }

    public ErrorData getErrorData() {
        return errorData;
    }
}
