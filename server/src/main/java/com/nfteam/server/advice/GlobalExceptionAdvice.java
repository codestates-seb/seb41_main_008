package com.nfteam.server.advice;

import com.nfteam.server.dto.response.error.ErrorResponse;
import com.nfteam.server.exception.NFTCustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

import static com.nfteam.server.exception.ExceptionCode.*;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

    private static final String LOG_MESSAGE = "Exception Code : {}, Exception_Message : {}, Exception_Class : {}";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        log.warn(LOG_MESSAGE, INVALID_METHOD_ARGS, exception.getMessage(), exception.getClass().getSimpleName());

        StringBuilder message = new StringBuilder();
        exception.getBindingResult().getFieldErrors().forEach(
                e -> message.append(e.getDefaultMessage()).append(" / ")
        );

        return ResponseEntity.badRequest()
                .body(new ErrorResponse(INVALID_METHOD_ARGS.getValue(),
                        message.deleteCharAt(message.lastIndexOf("/")).toString()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException exception) {
        log.warn(LOG_MESSAGE, INVALID_PATH_VARIABLE_ARGS, exception.getMessage(), exception.getClass().getSimpleName());

        StringBuilder message = new StringBuilder();
        exception.getConstraintViolations().forEach(
                e -> message.append(e.getMessage()).append("/")
        );

        return ResponseEntity.badRequest()
                .body(new ErrorResponse(INVALID_PATH_VARIABLE_ARGS.getValue(),
                        message.deleteCharAt(message.lastIndexOf("/")).toString()));
    }

    @ExceptionHandler(NFTCustomException.class)
    public ResponseEntity<ErrorResponse> handleNFTCustomException(NFTCustomException exception) {
        log.warn(LOG_MESSAGE, exception.getExceptionCode(), exception.getMessage(), exception.getClass().getSimpleName());
        return ResponseEntity.badRequest()
                .body(ErrorResponse.of(exception));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        log.warn(LOG_MESSAGE, METHOD_NOT_ALLOWED.value(), exception.getMessage(), exception.getClass().getSimpleName());
        return ResponseEntity.badRequest()
                .body(new ErrorResponse(METHOD_NOT_ALLOWED.name(), METHOD_NOT_ALLOWED.getReasonPhrase()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException exception) {
        return ResponseEntity.internalServerError()
                .body(new ErrorResponse(INTERNAL_SERVER_ERROR.getValue(), exception.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnhandledException(Exception exception) {
        log.warn(LOG_MESSAGE, INTERNAL_SERVER_ERROR, exception.getMessage(), exception.getClass().getSimpleName());
        return ResponseEntity.internalServerError()
                .body(new ErrorResponse(INTERNAL_SERVER_ERROR.getValue(), exception.getMessage()));
    }

}