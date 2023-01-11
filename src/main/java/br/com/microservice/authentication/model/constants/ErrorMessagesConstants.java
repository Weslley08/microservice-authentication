package br.com.microservice.authentication.model.constants;

public class ErrorMessagesConstants extends BaseConstants {
    public static final String[] STRINGS_ERRORS_PASSWORD = new String[]{
            "Deve conter 8 caracteres no mínimo", "Deve conter 1 Letra Maiúscula no mínimo",
            "Deve conter 1 Número no mínimo", "1 Símbolo no mínimo: $*&@#", "Não deve conter caracteres emse sequencia"
    };

    public static final String OPERADOR_NAO_AUTORIZADO = "Operador não autorizado";
    public static final String ACAO_BLOQUEADA_USUARIO = "Usuario não permite a redefinição de senha";
    public static final String CREDENCIAIS_INCORRETAS = "Credenciais incorretas";
}
