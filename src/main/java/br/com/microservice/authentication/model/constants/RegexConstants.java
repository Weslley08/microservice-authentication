package br.com.microservice.authentication.model.constants;

public class RegexConstants extends BaseConstants {

    public static final String REGEX_PASSWORD = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[$*&@#])(?:([0-9a-zA-Z$*&@#])(?!\\1)){8,}$";
}
