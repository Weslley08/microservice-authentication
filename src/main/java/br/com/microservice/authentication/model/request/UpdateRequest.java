package br.com.microservice.authentication.model.request;

import br.com.microservice.authentication.model.enums.Role;
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
    private Role newRole;
    @JsonProperty(value = "id_operador", access = JsonProperty.Access.WRITE_ONLY)
    private String idOperador;

    public UpdateRequest() {
    }

    public UpdateRequest(String username, String newPass, Role role) {
        this.newUsername = username;
        this.newPass = newPass;
        this.newRole = role;
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

    public Role getNewRole() {
        return newRole;
    }

    public void setNewRole(Role newRole) {
        this.newRole = newRole;
    }

    public String getIdOperador() {
        return idOperador;
    }

    public void setIdOperador(String idOperador) {
        this.idOperador = idOperador;
    }
}
