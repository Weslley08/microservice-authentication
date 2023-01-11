package br.com.microservice.authentication.exception;

import br.com.microservice.authentication.model.ResponseEntityCustom;
import br.com.microservice.authentication.model.dto.CampoError;
import br.com.microservice.authentication.model.dto.ErrorData;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

import static br.com.microservice.authentication.model.constants.TasksErrorConstants.ERRO_VALIDACAO_CAMPOS;

@RestControllerAdvice
public class ExceptionInterceptor {

    private final MessageSource messageSource;

    public ExceptionInterceptor(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<ErrorData> handleException(InternalServerErrorException ex) {
        return ResponseEntityCustom.internalServerError(ex.getErrorData());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundErrorException.class)
    public ResponseEntity<ErrorData> handleException(NotFoundErrorException ex) {
        return ResponseEntityCustom.notFound(ex.getErrorData());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestErrorException.class)
    public ResponseEntity<ErrorData> handleException(BadRequestErrorException ex) {
        return ResponseEntityCustom.badRequest(ex.getErrorData());
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(UnprocessableEntityErrorException.class)
    public ResponseEntity<ErrorData> handleException(UnprocessableEntityErrorException ex) {
        return ResponseEntityCustom.unprocessableEntity(ex.getErrorData());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenErrorException.class)
    public ResponseEntity<ErrorData> handleException(ForbiddenErrorException ex) {
        return ResponseEntityCustom.forbidden(ex.getErrorData());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedErrorException.class)
    public ResponseEntity<ErrorData> handleException(UnauthorizedErrorException ex) {
        return ResponseEntityCustom.unauthorized(ex.getErrorData());
    }

    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler({HttpMediaTypeNotAcceptableException.class})
    public ResponseEntity<ErrorData> mediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex) {
        ErrorData errorData = new ErrorData(ex.getBody().getTitle(), ex.getBody().getDetail());
        return ResponseEntityCustom.unsupportedMediaType(errorData);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorData> handleException(MethodArgumentNotValidException ex) {

        List<CampoError> campos = new ArrayList<>();
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        for (FieldError fieldError : fieldErrors) {
            String mensagem = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
            CampoError campoError = new CampoError(fieldError.getField(), mensagem);
            campos.add(campoError);
        }
        ErrorData erro = new ErrorData(ERRO_VALIDACAO_CAMPOS, campos);
        return ResponseEntityCustom.badRequest(erro);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorData> handleException() {
        List<CampoError> campos = new ArrayList<>();
        campos.add(new CampoError("body", "objeto body requerido"));
        ErrorData erro = new ErrorData(
                ERRO_VALIDACAO_CAMPOS, campos
        );
        return ResponseEntityCustom.badRequest(erro);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorData> handleException(MissingServletRequestParameterException ex) {
        List<CampoError> campos = new ArrayList<>();
        campos.add(new CampoError(ex.getParameterName(), ex.getBody().getDetail()));
        ErrorData erro = new ErrorData(
                ERRO_VALIDACAO_CAMPOS, campos
        );
        return ResponseEntityCustom.badRequest(erro);
    }
}
