package br.com.microservice.authentication.helper;

import br.com.microservice.authentication.model.enums.FilesEnum;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import static br.com.microservice.authentication.model.constants.SecurityConstants.SRC_MAIN_RESOURCES_KEYS;
import static br.com.microservice.authentication.model.enums.FilesEnum.*;

public class KeyToolHelper {

    private static final KeyFactory keyFactory;
    private static final Map<FilesEnum, String> FILES_STREAM = new HashMap<>();

    static {
        try {
            keyFactory = KeyFactory.getInstance("RSA");

            FILES_STREAM.put(ACCESS_PUBLIC_KEY, SRC_MAIN_RESOURCES_KEYS + "access_token/publicKey.der");
            FILES_STREAM.put(ACCESS_PRIVATE_KEY, SRC_MAIN_RESOURCES_KEYS + "access_token/privateKey.der");

            FILES_STREAM.put(REFRESH_PUBLIC_KEY, SRC_MAIN_RESOURCES_KEYS + "refresh_token/publicKey.der");
            FILES_STREAM.put(REFRESH_PRIVATE_KEY, SRC_MAIN_RESOURCES_KEYS + "refresh_token/privateKey.der");

            FILES_STREAM.put(RESET_PASSWORD_PUBLIC_KEY, SRC_MAIN_RESOURCES_KEYS + "change_password_token/publicKey.der");
            FILES_STREAM.put(RESET_PASSWORD_PRIVATE_KEY, SRC_MAIN_RESOURCES_KEYS + "change_password_token/privateKey.der");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[] loadKey(String file) {
        try {
            Path path = Paths.get(file);
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static RSAPrivateKey getEcPrivateKey(FilesEnum filePrivateKey) {
        try {
            return (RSAPrivateKey) keyFactory.generatePrivate(
                    new PKCS8EncodedKeySpec(
                            loadKey(FILES_STREAM.get(filePrivateKey))
                    )
            );
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    private static RSAPublicKey getEcPublicKey(FilesEnum filePublicKey) {
        try {
            return (RSAPublicKey) keyFactory.generatePublic(
                    new X509EncodedKeySpec(
                            loadKey(FILES_STREAM.get(filePublicKey))
                    )
            );
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    static RSAPrivateKey loadAccessPrivateKey() {
        return getEcPrivateKey(ACCESS_PRIVATE_KEY);
    }

    static RSAPublicKey loadAccessPublicKey() {
        return getEcPublicKey(ACCESS_PUBLIC_KEY);
    }

    static RSAPrivateKey loadRefreshPrivateKey() {
        return getEcPrivateKey(REFRESH_PRIVATE_KEY);
    }

    static RSAPublicKey loadRefreshPublicKey() {
        return getEcPublicKey(REFRESH_PUBLIC_KEY);
    }

    static RSAPrivateKey loadResetPasswordPrivateKey() {
        return getEcPrivateKey(RESET_PASSWORD_PRIVATE_KEY);
    }

    static RSAPublicKey loadResetPasswordPublicKey() {
        return getEcPublicKey(RESET_PASSWORD_PUBLIC_KEY);
    }
}
