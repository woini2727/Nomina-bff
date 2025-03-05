package com.bff.nomina.nomina.config.exception;


import com.bff.nomina.nomina.config.ErrorCode;

public abstract class GenericException extends RuntimeException {

    private final ErrorCode errorCode;

    protected GenericException(ErrorCode errorCode) {
        super(errorCode.getReasonPhrase());
        this.errorCode = errorCode;
    }

    public ErrorCode getCode() {
        return this.errorCode;
    }

}
