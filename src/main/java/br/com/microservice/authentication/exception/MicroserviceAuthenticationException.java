package br.com.microservice.authentication.exception;

import br.com.microservice.authentication.MicroserviceAuthenticationApplication;
import br.com.microservice.authentication.model.dto.ErrorData;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MicroserviceAuthenticationException extends RuntimeException {

    private static final Logger LOGGER = LoggerFactory.getLogger(MicroserviceAuthenticationApplication.class);
    protected final ErrorData errorData;

    public MicroserviceAuthenticationException(@NotNull ErrorData errorData) {
        super("Authentication Exception: " + errorData.toString());
        LOGGER.error(errorData.toString());
        this.errorData = errorData;
    }
}
