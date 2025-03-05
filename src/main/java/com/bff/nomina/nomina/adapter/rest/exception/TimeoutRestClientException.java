package com.bff.nomina.nomina.adapter.rest.exception;


import com.bff.nomina.nomina.config.ErrorCode;
import com.bff.nomina.nomina.config.exception.GenericException;

public final class TimeoutRestClientException extends GenericException {

    public TimeoutRestClientException(ErrorCode errorCode) {
        super(errorCode);
    }

}
