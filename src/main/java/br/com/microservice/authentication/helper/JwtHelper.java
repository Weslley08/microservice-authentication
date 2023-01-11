package br.com.microservice.authentication.helper;

import br.com.microservice.authentication.exception.ForbiddenErrorException;
import br.com.microservice.authentication.model.dto.ErrorData;
import br.com.microservice.authentication.model.enums.TypeTokenEnum;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import static br.com.microservice.authentication.helper.KeyToolHelper.*;
import static br.com.microservice.authentication.model.constants.BaseConstants.APP_NAME;
import static br.com.microservice.authentication.model.constants.SecurityConstants.*;
import static br.com.microservice.authentication.model.constants.TasksErrorConstants.TOKEN_EXPIRADO;

public class JwtHelper {

    private static Algorithm algorithm;
    private static Date expiresAt;

    public static String createJwtForClaims(final String subject,
                                            final Map<String, String> claims,
                                            final TypeTokenEnum tokenEnum) {
        verifyTypeToken(tokenEnum);
        final JWTCreator.Builder jwtBuilder = JWT
                .create()
                .withSubject(subject)
                .withJWTId(UUID.randomUUID().toString())
                .withIssuer(APP_NAME);
        claims.forEach(jwtBuilder::withClaim);
        return jwtBuilder
                .withIssuedAt(new Date())
                .withNotBefore(new Date())
                .withExpiresAt(expiresAt)
                .sign(algorithm);
    }

    public static DecodedJWT decodedJWT(String tokenJwt, TypeTokenEnum tokenEnum) {
        try {
            verifyTypeToken(tokenEnum);
            return JWT.require(algorithm)
                    .withIssuer(APP_NAME)
                    .build()
                    .verify(tokenJwt.replace(TOKEN_PREFIX, ""));
        } catch (TokenExpiredException e) {
            throw new ForbiddenErrorException(new ErrorData(TOKEN_EXPIRADO,
                    "Expirado em: " + e.getExpiredOn().toString()));
        }
    }

    private static void verifyTypeToken(TypeTokenEnum typeToken) {
        RSAPublicKey rsaPublicKey = null;
        RSAPrivateKey rsaPrivateKey = null;

        switch (typeToken) {
            case ACCESS_TOKEN -> {
                rsaPublicKey = loadAccessPublicKey();
                rsaPrivateKey = loadAccessPrivateKey();
                expiresAt = new Date(System.currentTimeMillis() + EXPIRATION_TIME_ACCESS_TOKEN);
            }
            case REFRESH_TOKEN -> {
                rsaPublicKey = loadRefreshPublicKey();
                rsaPrivateKey = loadRefreshPrivateKey();
                expiresAt = new Date(System.currentTimeMillis() + EXPIRATION_TIME_REFRESH_TOKEN);
            }
            case RESET_PASSWORD_TOKEN -> {
                rsaPublicKey = loadResetPasswordPublicKey();
                rsaPrivateKey = loadResetPasswordPrivateKey();
                expiresAt = new Date(System.currentTimeMillis() + EXPIRATION_TIME_RESET_PASSWORD_TOKEN);
            }
        }
        algorithm = Algorithm.RSA512(rsaPublicKey, rsaPrivateKey);
    }
}