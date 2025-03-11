package com.df.admin.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(StudentException.class)
    public ResponseEntity<ApiException> handleStudentException(StudentException ex) {
        ApiException apiException = new ApiException(
                ex.errorCode.getCode(),
                ex.errorCode.getError(),
                ex.getMessage()
        );
        return ResponseEntity.badRequest().body(apiException);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiException> handleValidationExceptions(MethodArgumentNotValidException ex) {
        StringBuilder errors = new StringBuilder();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.append(error.getField() + " : " + error.getDefaultMessage() + " ")
        );
        ApiException apiException = new ApiException(
                ErrorCode.BAD_REQUEST.getCode(),
                ErrorCode.BAD_REQUEST.getError(),
                errors.toString()
        );
        return ResponseEntity.badRequest().body(apiException);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiException> handleGenericException(Exception ex) {
        ApiException apiException = new ApiException(
                500,
                "Internal Server Error - Error no manejado",
                ex.getMessage()
        );
        return ResponseEntity.internalServerError().body(apiException);
    }
}
