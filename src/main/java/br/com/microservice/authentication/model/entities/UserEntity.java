package br.com.microservice.authentication.model.entities;

import br.com.microservice.authentication.model.enums.RoleEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_auth")
public class UserEntity {

    @Id
    @Column(nullable = false)
    private String userId;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private RoleEnum roleEnum;
    @Column(nullable = false)
    private Boolean isInactive;
    @Column(nullable = false)
    private Boolean notResetPassword;

    public UserEntity() {
    }

    public UserEntity(String userId, String username,
                      String password, RoleEnum roleEnum,
                      Boolean isInactive, Boolean notResetPassword) {
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
