package br.com.microservice.authentication.model.constants;

public class RoutesConstants {

    public static final String BASE_PATH = "/microservice-authentication";
    public static final String BASE_PATH_AND_V1 = BASE_PATH + "/v1";
    public static final String USERS_PATH = "/users";
    public static final String TOKENS_PATH = "/tokens";
    public static final String TOKENS_AND_REFRESH_PATH = "/tokens/refresh";
    public static final String TOKENS_AND_CHANGE_PASS_PATH = "/tokens/changePass/{username}";
    public static final String ACTUATOR_URL = BASE_PATH + "/back-service/**";
    public static final String SWAGGER_UI_URL = BASE_PATH + "/docs/**";
    public static final String H2_DB_URL = BASE_PATH + "/testes-unitarios-h2/**";
    public static final String CREATE_USER_URL = BASE_PATH_AND_V1 + USERS_PATH;
    public static final String TOKEN_USER_URL = BASE_PATH_AND_V1 + TOKENS_PATH;
    public static final String TOKEN_REFRESH_USER_URL = BASE_PATH_AND_V1 + TOKENS_AND_REFRESH_PATH;
    public static final String TOKEN_RESET_PASS_USER_URL = BASE_PATH_AND_V1 + TOKENS_AND_CHANGE_PASS_PATH;
}
