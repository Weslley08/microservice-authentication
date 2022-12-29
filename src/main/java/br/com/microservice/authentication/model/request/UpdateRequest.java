package br.com.microservice.authentication.model.request;

import br.com.microservice.authentication.model.enums.Role;
import com.fasterxml.jackson.annotation.JsonProperty;


public class UpdateRequest {

    @JsonProperty(value = "username", access = JsonProperty.Access.WRITE_ONLY)
    private String username;
    @JsonProperty(value = "new_pass", access = JsonProperty.Access.WRITE_ONLY)
    private String newPass;
    @JsonProperty(value = "role", access = JsonProperty.Access.WRITE_ONLY)
    private Role role = Role.USER;
    @JsonProperty(value = "is_inactive", access = JsonProperty.Access.WRITE_ONLY)
    private Boolean isInactive = Boolean.FALSE;
    @JsonProperty(value = "not_reset_password", access = JsonProperty.Access.WRITE_ONLY)
    private Boolean notResetPassword = Boolean.FALSE;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Boolean getInactive() {
        return isInactive;
    }

    public void setInactive(Boolean inactive) {
        isInactive = inactive;
    }

    public Boolean getNotResetPassword() {
        return notResetPassword;
    }

    public void setNotResetPassword(Boolean notResetPassword) {
        this.notResetPassword = notResetPassword;
    }
}
