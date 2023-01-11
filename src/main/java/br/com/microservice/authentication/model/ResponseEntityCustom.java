package br.com.microservice.authentication.model;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.util.MultiValueMap;

public class ResponseEntityCustom<T> extends ResponseEntity<T> {

    public ResponseEntityCustom(HttpStatusCode status) {
        super(status);
    }

    public ResponseEntityCustom(T body, HttpStatusCode status) {
        super(body, status);
    }

    public ResponseEntityCustom(MultiValueMap<String, String> headers, HttpStatusCode status) {
        super(headers, status);
    }

    public ResponseEntityCustom(T body, MultiValueMap<String, String> headers, HttpStatusCode status) {
        super(body, headers, status);
    }

    public ResponseEntityCustom(T body, MultiValueMap<String, String> headers, int rawStatus) {
        super(body, headers, rawStatus);
    }

    public static <T> ResponseEntity<T> ok(@Nullable T body) {
        return ok()
                .headers(createHeadersCustom())
                .body(body);
    }

    public static <T> ResponseEntity<T> created(@Nullable T body) {
        return status(HttpStatus.CREATED)
                .headers(createHeadersCustom())
                .body(body);
    }

    public static <T> ResponseEntity<T> accepted(@Nullable T body) {
        return status(HttpStatus.ACCEPTED)
                .headers(createHeadersCustom())
                .body(body);
    }

    public static <T> ResponseEntity<T> noContent(@Nullable T body) {
        return status(HttpStatus.NO_CONTENT)
                .headers(createHeadersCustom())
                .body(body);
    }

    public static <T> ResponseEntity<T> badRequest(@Nullable T body) {
        return status(HttpStatus.BAD_REQUEST)
                .headers(createHeadersCustom())
                .body(body);
    }

    public static <T> ResponseEntity<T> forbidden(@Nullable T body) {
        return status(HttpStatus.FORBIDDEN)
                .headers(createHeadersCustom())
                .body(body);
    }

    public static <T> ResponseEntity<T> unauthorized(@Nullable T body) {
        return status(HttpStatus.UNAUTHORIZED)
                .headers(createHeadersCustom())
                .body(body);
    }

    public static <T> ResponseEntity<T> unsupportedMediaType(@Nullable T body) {
        return status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .headers(createHeadersCustom())
                .body(body);
    }

    public static <T> ResponseEntity<T> notFound(@Nullable T body) {
        return status(HttpStatus.NOT_FOUND)
                .headers(createHeadersCustom())
                .body(body);
    }

    public static <T> ResponseEntity<T> unprocessableEntity(@Nullable T body) {
        return status(HttpStatus.UNPROCESSABLE_ENTITY)
                .headers(createHeadersCustom())
                .body(body);
    }

    public static <T> ResponseEntity<T> internalServerError(@Nullable T body) {
        return status(HttpStatus.INTERNAL_SERVER_ERROR)
                .headers(createHeadersCustom())
                .body(body);
    }


    private static HttpHeaders createHeadersCustom() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("api-version", "1");
        return httpHeaders;
    }
}
