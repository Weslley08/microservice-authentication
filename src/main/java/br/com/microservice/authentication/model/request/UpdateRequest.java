package br.com.microservice.authentication.model.request;

import br.com.microservice.authentication.model.enums.RoleEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateRequest {

    @JsonProperty(value = "new_username", access = JsonProperty.Access.WRITE_ONLY)
    private String newUsername;
    @JsonProperty(value = "new_pass", access = JsonProperty.Access.WRITE_ONLY)
    private String newPass;
    @JsonProperty(value = "new_role", access = JsonProperty.Access.WRITE_ONLY)
    private RoleEnum newRoleEnum;
    @JsonProperty(value = "id_operador", access = JsonProperty.Access.WRITE_ONLY)
    private String idOperador;

    public UpdateRequest() {
    }

    public UpdateRequest(String username, String newPass, RoleEnum roleEnum) {
        this.newUsername = username;
        this.newPass = newPass;
        this.newRoleEnum = roleEnum;
    }

    public String getNewUsername() {
        return newUsername;
    }

    public void setNewUsername(String newUsername) {
        this.newUsername = newUsername;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

    public RoleEnum getNewRole() {
        return newRoleEnum;
    }

    public void setNewRole(RoleEnum newRoleEnum) {
        this.newRoleEnum = newRoleEnum;
    }

    public String getIdOperador() {
        return idOperador;
    }

    public void setIdOperador(String idOperador) {
        this.idOperador = idOperador;
    }
}
