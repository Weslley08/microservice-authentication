package br.com.microservice.authentication.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import static br.com.microservice.authentication.mapper.ModelUtilsMapper.fromObject;

public class ResponseData {

    @JsonProperty("data")
    private Object data;

    public <T> T getDataAs(Class<T> targetClass) {
        return fromObject(this.data, targetClass);
    }
    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }

}
