package br.com.microservice.authentication.model.constants;

import org.springframework.http.HttpMethod;

public class SecurityConstants extends BaseConstants {


    public static final String SRC_MAIN_RESOURCES_KEYS = "./src/main/resources/keys/";
    public static final String[] KEYS_PATH = new String[]{"access_token", "refresh_token", "change_password_token"
    };
    public static final String ALLOWED_ALL = "*";
    public static final String[] ALLOWED_HEADERS = new String[]{"Authorization", "Content-Type", "Accept"};
    public static final String[] EXPOSED_ALLOWED_HEADERS = new String[]{"Origin", "Authorization", "Content-Type", "Accept"};
    public static final String[] ALLOWED_URL = new String[]{ALLOWED_ALL, "/microservice-user/v1"};
    public static final String[] ALLOWED_METHODS = new String[]{HttpMethod.GET.name(), HttpMethod.PUT.name(), HttpMethod.POST.name()};
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final long EXPIRATION_TIME_RESET_PASSWORD_TOKEN = 500000;
    public static final long EXPIRATION_TIME_ACCESS_TOKEN = 900000;
    public static final long EXPIRATION_TIME_REFRESH_TOKEN = 1350000;
    public static final String HEADER_ACCESS_TOKEN_STRING = "Authorization";
    public static final String HEADER_RESET_PASSWORD_TOKEN_STRING = "reset_password_token";
}