package br.com.microservice.authentication.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import static br.com.microservice.authentication.model.constants.SecurityConstants.TOKEN_PREFIX;

public class JwtDto {

    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("type")
    private String typeToken;

    public JwtDto(String accessToken) {
        this.accessToken = accessToken;
        this.typeToken = TOKEN_PREFIX;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTypeToken() {
        return typeToken;
    }

    public void setTypeToken(String typeToken) {
        this.typeToken = typeToken;
    }
}
