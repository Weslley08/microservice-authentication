package br.com.microservice.authentication.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

public class ErrorData {

    @JsonProperty("title")
    private String title;
    @JsonProperty("detail")
    private String detail;
    @JsonProperty("timestamp")
    private String timestamp;
    @JsonProperty("campo_error")
    private List<CampoError> campoErrors;


    public ErrorData(String title, String detail) {
        this.title = title;
        this.detail = detail;
        this.timestamp = String.valueOf(new Timestamp(System.currentTimeMillis()));
        this.campoErrors = Collections.emptyList();
    }

    public ErrorData(String title, String detail, List<CampoError> campoErrors) {
        this.title = title;
        this.detail = detail;
        this.timestamp = String.valueOf(new Timestamp(System.currentTimeMillis()));
        this.campoErrors = campoErrors;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public List<CampoError> getCampoErrors() {
        return campoErrors;
    }

    public void setCampoErrors(List<CampoError> campoErrors) {
        this.campoErrors = campoErrors;
    }
}
