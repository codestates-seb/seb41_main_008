package com.nfteam.server.dto;

import com.nfteam.server.exception.ExceptionCode;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

@Getter
public class ErrorResponse {

    private final ExceptionInfo exceptionInfo;
    private final List<FieldError> fieldErrors;
    private final List<ConstraintViolationError> violationErrors;

    public ErrorResponse(ExceptionInfo exceptionInfo, List<FieldError> fieldErrors, List<ConstraintViolationError> violationErrors) {
        this.exceptionInfo = exceptionInfo;
        this.fieldErrors = fieldErrors;
        this.violationErrors = violationErrors;
    }

    private ErrorResponse(List<FieldError> fieldErrors, List<ConstraintViolationError> violationErrors) {
        this.exceptionInfo = ExceptionInfo.of(HttpStatus.BAD_REQUEST);
        this.fieldErrors = fieldErrors;
        this.violationErrors = violationErrors;
    }

    public static ErrorResponse of(ExceptionCode exceptionCode) {
        return new ErrorResponse(ExceptionInfo.of(exceptionCode), null, null);
    }

    public static ErrorResponse of(HttpStatus httpStatus) {
        return new ErrorResponse(ExceptionInfo.of(httpStatus), null, null);
    }

    public static ErrorResponse of(BindingResult bindingResult) {
        return new ErrorResponse(FieldError.of(bindingResult), null);
    }

    public static ErrorResponse of(Set<ConstraintViolation<?>> violations) {
        return new ErrorResponse(null, ConstraintViolationError.of(violations));
    }

    @Getter
    public static class ExceptionInfo {
        private final int status;
        private final String message;

        private ExceptionInfo(int status, String message) {
            this.status = status;
            this.message = message;
        }

        public static ExceptionInfo of(HttpStatus httpStatus) {
            return new ExceptionInfo(httpStatus.value(), httpStatus.getReasonPhrase());
        }

        public static ExceptionInfo of(ExceptionCode exceptionCode) {
            return new ExceptionInfo(exceptionCode.getStatus(), exceptionCode.getMessage());
        }
    }

    @Getter
    public static class FieldError {
        private final String field;
        private final Object rejectedValue;
        private final String reason;

        private FieldError(String field, Object rejectedValue, String reason) {
            this.field = field;
            this.rejectedValue = rejectedValue;
            this.reason = reason;
        }

        public static List<FieldError> of(BindingResult bindingResult) {
            final List<org.springframework.validation.FieldError> fieldErrors
                = bindingResult.getFieldErrors();

            return fieldErrors.stream()
                .map(error -> new FieldError(
                    error.getField(),
                    error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                    error.getDefaultMessage()
                )).collect(Collectors.toList());
        }
    }

    @Getter
    public static class ConstraintViolationError {
        private final Object rejectedValue;
        private final String reason;

        private ConstraintViolationError(Object rejectedValue, String reason) {
            this.rejectedValue = rejectedValue;
            this.reason = reason;
        }

        public static List<ConstraintViolationError> of(
            Set<ConstraintViolation<?>> constraintViolations) {
            return constraintViolations.stream()
                .map(constraintViolation -> new ConstraintViolationError(
                    constraintViolation.getInvalidValue().toString(),
                    constraintViolation.getMessage()
                )).collect(Collectors.toList());
        }
    }
}
