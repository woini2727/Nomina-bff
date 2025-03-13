package com.bff.nomina.nomina.config;

import lombok.Getter;

import java.io.Serializable;

public class ErrorCode implements Serializable {

    public static final ErrorCode INTERNAL_ERROR = new ErrorCode("999", "Internal server error");
    public static final ErrorCode BAD_REQUEST = new ErrorCode("101", "Bad request");
    public static final ErrorCode NOMINA_NOT_FOUND = new ErrorCode("103", "Nomina not found");
    public static final ErrorCode TIMEOUT = new ErrorCode("105", "Timeout");

    private final String value;
    @Getter
    private final String reasonPhrase;

    public ErrorCode(final String value, final String reasonPhrase) {
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }

    public String value() {
        return this.value;
    }

}