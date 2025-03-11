package com.df.admin.exception;

public class StudentException extends RuntimeException {
    ErrorCode errorCode;

    public StudentException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
