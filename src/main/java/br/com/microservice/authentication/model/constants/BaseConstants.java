package br.com.microservice.authentication.model.constants;

public class BaseConstants {

    public static final String APP_NAME = "microservice-authentication";
    public static final String USERNAME = "username";
    public static final String AUTHORITIES = "authorities";
    public static final String PASSWORD = "password";
    public static final String ROLE_ = "ROLE_";

    public static String userAndId(String id) {
        return USERNAME + ": " + id;
    }
}
