package com.bff.nomina.nomina.config.exception;


import com.bff.nomina.nomina.config.ErrorCode;

public final class ForbiddenException extends GenericException {

    public ForbiddenException(ErrorCode errorCode) {
        super(errorCode);
    }

}
