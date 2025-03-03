package com.bff.nomina.nomina.config;

import java.io.Serializable;

public record ErrorCode(String value, String reasonPhrase) implements Serializable {

    public static final ErrorCode INTERNAL_ERROR = new ErrorCode("999", "Internal server error");
    public static final ErrorCode BAD_REQUEST = new ErrorCode("101", "Bad request");
    public static final ErrorCode RESOURCE_NOT_FOUND = new ErrorCode("102", "Not found");
    public static final ErrorCode NOMINA_NOT_FOUND = new ErrorCode("103", "Star Wars character not found");
    public static final ErrorCode FORBIDDEN = new ErrorCode("104", "Not allowed to access the resource");
    public static final ErrorCode TIMEOUT = new ErrorCode("105", "Timeout");

}
