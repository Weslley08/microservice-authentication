package br.com.microservice.authentication.model.dto;

import br.com.microservice.authentication.model.enums.RoleEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.UUID;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    @JsonProperty(value = "user_id", access = JsonProperty.Access.READ_ONLY)
    private String userId = UUID.randomUUID().toString();
    @NotEmpty
    @JsonProperty(value = "username", access = JsonProperty.Access.READ_WRITE)
    @Size(min = 5, message = "O campo username não atingiu ao tamanho minimo")
    private String username;
    @NotEmpty
    @JsonProperty(value = "password", access = JsonProperty.Access.WRITE_ONLY)
    @Size(min = 8, message = "O campo password não atingiu ao tamanho minimo")
    private String password;
    @JsonProperty(value = "role", access = JsonProperty.Access.READ_ONLY)
    private RoleEnum roleEnum = RoleEnum.USER;
    @JsonProperty(value = "is_inactive", access = JsonProperty.Access.READ_ONLY)
    private Boolean isInactive = Boolean.FALSE;
    @JsonProperty(value = "not_reset_password", access = JsonProperty.Access.READ_WRITE)
    private Boolean notResetPassword = Boolean.FALSE;

    public UserDto() {
    }

    public UserDto(String userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    public UserDto(String userId, String username, String password, RoleEnum roleEnum, Boolean isInactive, Boolean notResetPassword) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.roleEnum = roleEnum;
        this.isInactive = isInactive;
        this.notResetPassword = notResetPassword;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RoleEnum getRole() {
        return roleEnum;
    }

    public void setRole(RoleEnum roleEnum) {
        this.roleEnum = roleEnum;
    }

    public Boolean getIsInactive() {
        return isInactive;
    }

    public void setIsInactive(Boolean isInactive) {
        this.isInactive = isInactive;
    }

    public Boolean getNotResetPassword() {
        return notResetPassword;
    }

    public void setNotResetPassword(Boolean notResetPassword) {
        this.notResetPassword = notResetPassword;
    }
}
