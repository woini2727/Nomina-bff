package com.bff.nomina.nomina.config.exception;


import com.bff.nomina.nomina.config.ErrorCode;

public final class NotFoundException extends GenericException {

    public NotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

}
