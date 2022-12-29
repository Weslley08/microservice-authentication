package br.com.microservice.authentication.exception;

import br.com.microservice.authentication.model.dto.CampoError;
import br.com.microservice.authentication.model.dto.ErrorData;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;


@RestControllerAdvice
public class ExceptionInterceptor {

    private final MessageSource messageSource;

    public ExceptionInterceptor(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<ErrorData> handleException(InternalServerErrorException ex, WebRequest request) {
        return ResponseEntity.internalServerError().body(ex.errorData);
    }

    @ExceptionHandler(NotFoundErrorException.class)
    public ResponseEntity<ErrorData> handleException(NotFoundErrorException ex, WebRequest request) {
        return new ResponseEntity<>(ex.errorData, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestErrorException.class)
    public ResponseEntity<ErrorData> handleException(BadRequestErrorException ex, WebRequest request) {
        return ResponseEntity.badRequest().body(ex.errorData);
    }

    @ExceptionHandler(UnprocessableEntityErrorException.class)
    public ResponseEntity<ErrorData> handleException(UnprocessableEntityErrorException ex, WebRequest request) {
        return ResponseEntity.unprocessableEntity().body(ex.errorData);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorData> handleException(MethodArgumentNotValidException ex) {

        List<CampoError> campos = new ArrayList<>();
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        for (FieldError fieldError : fieldErrors) {
            String mensagem = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
            CampoError campoError = new CampoError(fieldError.getField(), mensagem);
            campos.add(campoError);
        }
        ErrorData erro = new ErrorData(
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "erro na validação dos campos", campos
        );
        return ResponseEntity.badRequest().body(erro);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorData> handleException(HttpMessageNotReadableException ex) {
        List<CampoError> campos = new ArrayList<>();
        campos.add(new CampoError("body", "objeto body requerido"));
        ErrorData erro = new ErrorData(
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "erro na validação dos campos", campos
        );
        return ResponseEntity.badRequest().body(erro);
    }
}
