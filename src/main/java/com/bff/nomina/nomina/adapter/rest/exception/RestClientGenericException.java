package com.bff.nomina.nomina.adapter.rest.exception;

import com.bff.nomina.nomina.config.ErrorCode;
import com.bff.nomina.nomina.config.exception.GenericException;

public final class RestClientGenericException extends GenericException {

    public RestClientGenericException(ErrorCode errorCode) {
        super(errorCode);
    }

}
