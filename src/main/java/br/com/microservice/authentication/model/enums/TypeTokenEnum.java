package br.com.microservice.authentication.model.enums;

import static br.com.microservice.authentication.model.constants.SecurityConstants.SRC_MAIN_RESOURCES_KEYS;

public enum TypeTokenEnum {
    ACCESS_TOKEN(
            SRC_MAIN_RESOURCES_KEYS + "access_token/privateKey.der",
            SRC_MAIN_RESOURCES_KEYS + "access_token/publicKey.der"
    ),
    REFRESH_TOKEN(
            SRC_MAIN_RESOURCES_KEYS + "refresh_token/privateKey.der",
            SRC_MAIN_RESOURCES_KEYS + "refresh_token/publicKey.der"
    ),
    RESET_PASSWORD_TOKEN(
            SRC_MAIN_RESOURCES_KEYS + "change_password_token/privateKey.der",
            SRC_MAIN_RESOURCES_KEYS + "change_password_token/publicKey.der"
    );

    private final String filePrivateKey;
    private final String filePublicKey;

    TypeTokenEnum(String filePrivateKey, String filePublicKey) {
        this.filePrivateKey = filePrivateKey;
        this.filePublicKey = filePublicKey;
    }

    public String getFilePrivateKey() {
        return filePrivateKey;
    }

    public String getFilePublicKey() {
        return filePublicKey;
    }
}
