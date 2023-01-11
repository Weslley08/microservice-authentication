package br.com.microservice.authentication.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

import static br.com.microservice.authentication.model.constants.SecurityConstants.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JwtDto {

    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("expires_access_at")
    private String expiresAccessAt;
    @JsonProperty("type")
    private String typeToken;
    @JsonProperty("refresh_token")
    private String refreshToken;
    @JsonProperty("expires_refresh_at")
    private String expiresRefreshAt;
    @JsonProperty("reset_pass_token")
    private String resetPassToken;
    @JsonProperty("expires_pass_at")
    private String expiresPassAt;

    public JwtDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.expiresAccessAt = new Date(System.currentTimeMillis() + EXPIRATION_TIME_ACCESS_TOKEN).toString();
        this.refreshToken = refreshToken;
        this.expiresRefreshAt = new Date(System.currentTimeMillis() + EXPIRATION_TIME_REFRESH_TOKEN).toString();
        this.typeToken = TOKEN_PREFIX;
    }

    public JwtDto(String resetPassToken) {
        this.resetPassToken = resetPassToken;
        this.expiresPassAt = new Date(System.currentTimeMillis() + EXPIRATION_TIME_RESET_PASSWORD_TOKEN).toString();
        this.typeToken = TOKEN_PREFIX;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getExpiresAccessAt() {
        return expiresAccessAt;
    }

    public void setExpiresAccessAt(String expiresAccessAt) {
        this.expiresAccessAt = expiresAccessAt;
    }

    public String getTypeToken() {
        return typeToken;
    }

    public void setTypeToken(String typeToken) {
        this.typeToken = typeToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getExpiresRefreshAt() {
        return expiresRefreshAt;
    }

    public void setExpiresRefreshAt(String expiresRefreshAt) {
        this.expiresRefreshAt = expiresRefreshAt;
    }

    public String getResetPassToken() {
        return resetPassToken;
    }

    public void setResetPassToken(String resetPassToken) {
        this.resetPassToken = resetPassToken;
    }

    public String getExpiresPassAt() {
        return expiresPassAt;
    }

    public void setExpiresPassAt(String expiresPassAt) {
        this.expiresPassAt = expiresPassAt;
    }
}
