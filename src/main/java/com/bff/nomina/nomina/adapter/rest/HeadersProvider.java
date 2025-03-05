package com.bff.nomina.nomina.adapter.rest;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class HeadersProvider {

    private static final String TRACE_ID_HEADER = "traceId";

    public HttpHeaders get() {
        final HttpHeaders headers = new HttpHeaders();
        //final String traceId = tracer.currentSpan().context().traceIdString();
        //headers.set(TRACE_ID_HEADER, traceId);
        return headers;
    }

}
