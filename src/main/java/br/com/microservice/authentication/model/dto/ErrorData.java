package br.com.microservice.authentication.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorData {

    @JsonProperty("error_task")
    private String errorTask;
    @JsonProperty("detail")
    private String detail;
    @JsonProperty("timestamp")
    private String timestamp;
    @JsonProperty("campo_error")
    private List<CampoError> campoErrors;


    public ErrorData(String errorTask) {
        this.errorTask = errorTask;
        this.timestamp = String.valueOf(new Timestamp(System.currentTimeMillis()));
    }

    public ErrorData(String errorTask, String detail) {
        this.errorTask = errorTask;
        this.detail = detail;
        this.timestamp = String.valueOf(new Timestamp(System.currentTimeMillis()));
    }

    public ErrorData(String errorTask, List<CampoError> campoErrors) {
        this.errorTask = errorTask;
        this.timestamp = String.valueOf(new Timestamp(System.currentTimeMillis()));
        this.campoErrors = campoErrors;
    }

    public String getErrorTask() {
        return errorTask;
    }

    public void setErrorTask(String errorTask) {
        this.errorTask = errorTask;
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
