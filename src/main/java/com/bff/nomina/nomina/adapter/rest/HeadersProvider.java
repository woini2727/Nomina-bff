package com.bff.nomina.nomina.adapter.rest;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class HeadersProvider {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    public HttpHeaders get(String jwt) {
        HttpHeaders headers =  new HttpHeaders();
        headers.add(AUTHORIZATION_HEADER, "Bearer ".concat(jwt));
        return headers;
    }

}
