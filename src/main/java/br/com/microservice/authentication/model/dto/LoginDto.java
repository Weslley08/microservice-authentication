package br.com.microservice.authentication.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class LoginDto {

    @NotEmpty
    @Size(min = 3, message = "O campo username não atingiu ao tamanho minimo")
    private String username;
    @NotEmpty
    @Size(min = 3, message = "O campo password não atingiu ao tamanho minimo")
    private String password;

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
}
