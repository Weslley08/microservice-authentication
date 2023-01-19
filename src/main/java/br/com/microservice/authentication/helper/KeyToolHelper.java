package br.com.microservice.authentication.helper;

import br.com.microservice.authentication.model.enums.TypeTokenEnum;

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

public class KeyToolHelper {

    private static final KeyFactory keyFactory;

    static {
        try {
            keyFactory = KeyFactory.getInstance("RSA");
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

    private static RSAPrivateKey getPrivateKey(TypeTokenEnum typeTokenEnum) {
        try {
            return (RSAPrivateKey) keyFactory.generatePrivate(
                    new PKCS8EncodedKeySpec(loadKey(typeTokenEnum.getFilePrivateKey()))
            );
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    private static RSAPublicKey getPublicKey(TypeTokenEnum typeTokenEnum) {
        try {
            return (RSAPublicKey) keyFactory.generatePublic(
                    new X509EncodedKeySpec(loadKey(typeTokenEnum.getFilePublicKey()))
            );
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    static RSAPrivateKey loadPrivateKey(TypeTokenEnum typeTokenEnum) {
        return getPrivateKey(typeTokenEnum);
    }

    static RSAPublicKey loadPublicKey(TypeTokenEnum typeTokenEnum) {
        return getPublicKey(typeTokenEnum);
    }
}
