package br.com.microservice.authentication.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CampoError {

    @JsonProperty("field")
    private String field;
    @JsonProperty("message")
    private String message;

    public CampoError(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
