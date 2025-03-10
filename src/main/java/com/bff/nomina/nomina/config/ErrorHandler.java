package com.bff.nomina.nomina.config;

import com.bff.nomina.nomina.adapter.rest.exception.BadRequestRestClientException;
import com.bff.nomina.nomina.adapter.rest.exception.RestClientGenericException;
import com.bff.nomina.nomina.adapter.rest.exception.TimeoutRestClientException;
import com.bff.nomina.nomina.config.exception.ForbiddenException;
import com.bff.nomina.nomina.config.exception.NotFoundException;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.net.SocketTimeoutException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Optional;

@Slf4j
@ControllerAdvice
public class ErrorHandler {

    private static final String FIELD_SEPARATOR = ":";
    private final HttpServletRequest httpServletRequest;

    private final Environment environment;

    public ErrorHandler(
            final HttpServletRequest httpServletRequest,
            Environment environment) {
        this.httpServletRequest = httpServletRequest;
        this.environment = environment;
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ApiErrorResponse> handleDefault(Throwable ex) {
        log.error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), ex);
        final ErrorCode errorCode = ErrorCode.INTERNAL_ERROR;
        return buildResponseError(HttpStatus.INTERNAL_SERVER_ERROR, errorCode.getReasonPhrase(), errorCode);
    }

    @ExceptionHandler(BadRequestRestClientException.class)
    public ResponseEntity<ApiErrorResponse> handleBadRequest(BadRequestRestClientException ex) {
        log.error(HttpStatus.BAD_REQUEST.getReasonPhrase(), ex);
        return buildResponseError(HttpStatus.BAD_REQUEST, ex, ex.getCode());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleBadRequest(ConstraintViolationException ex) {
        log.error(HttpStatus.BAD_REQUEST.getReasonPhrase(), ex);
        return buildResponseError(HttpStatus.BAD_REQUEST, ex, ErrorCode.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiErrorResponse> handleBadRequest(MethodArgumentTypeMismatchException ex) {
        log.error(HttpStatus.BAD_REQUEST.getReasonPhrase(), ex);
        final String message = "Parameter " + ex.getName() + " must be of type " +
                ex.getParameter().getParameterType().getSimpleName();
        return buildResponseError(HttpStatus.BAD_REQUEST, message, ErrorCode.BAD_REQUEST);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ApiErrorResponse> handleForbidden(ForbiddenException ex) {
        log.error(HttpStatus.FORBIDDEN.getReasonPhrase(), ex);
        return buildResponseError(HttpStatus.FORBIDDEN, ex, ex.getCode());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFound(NotFoundException ex) {
        log.error(HttpStatus.NOT_FOUND.getReasonPhrase(), ex);
        return buildResponseError(HttpStatus.NOT_FOUND, ex, ex.getCode());
    }

    @ExceptionHandler(TimeoutRestClientException.class)
    public ResponseEntity<ApiErrorResponse> handleTimeout(TimeoutRestClientException ex) {
        log.error(HttpStatus.GATEWAY_TIMEOUT.getReasonPhrase(), ex);
        return buildResponseError(HttpStatus.GATEWAY_TIMEOUT, ex, ex.getCode());
    }

    @ExceptionHandler(RestClientGenericException.class)
    public ModelAndView handleRestClient(RestClientGenericException ex) {
        log.error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), ex);
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", ex);
        mav.setViewName("error");
        return mav;

    }

    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<ApiErrorResponse> handle(ResourceAccessException ex) {
        if (ex.getCause() instanceof SocketTimeoutException) {
            final ErrorCode errorCode = ErrorCode.TIMEOUT;
            log.error(HttpStatus.GATEWAY_TIMEOUT.getReasonPhrase(), ex);
            return buildResponseError(HttpStatus.GATEWAY_TIMEOUT, errorCode.getReasonPhrase(), errorCode);
        }

        log.error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), ex);
        final ErrorCode errorCode = ErrorCode.INTERNAL_ERROR;
        return buildResponseError(HttpStatus.INTERNAL_SERVER_ERROR, errorCode.getReasonPhrase(), errorCode);
    }

    @ExceptionHandler(AsyncRequestTimeoutException.class)
    public ResponseEntity<ApiErrorResponse> handleTimeout(AsyncRequestTimeoutException ex) {
        log.error(HttpStatus.GATEWAY_TIMEOUT.getReasonPhrase(), ex);
        final ErrorCode errorCode = ErrorCode.TIMEOUT;
        return buildResponseError(HttpStatus.GATEWAY_TIMEOUT, errorCode.getReasonPhrase(), errorCode);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiErrorResponse> handleBadRequest(MissingServletRequestParameterException ex) {
        final String message =
                "Parameter " + ex.getParameterName() + " of type " + ex.getParameterType() + " is required";
        return buildResponseError(HttpStatus.BAD_REQUEST, message, ErrorCode.BAD_REQUEST);
    }

    private ResponseEntity<ApiErrorResponse> buildResponseError(
            final HttpStatus httpStatus,
            final Throwable ex,
            final ErrorCode errorCode
    ) {
        return buildResponseError(httpStatus, ex.getMessage(), errorCode);
    }

    private ResponseEntity<ApiErrorResponse> buildResponseError(
            final HttpStatus httpStatus,
            final String message,
            final ErrorCode errorCode
    ) {


        final String code =
                Optional.of(errorCode.value())
                        .filter(errorCodeValue -> errorCodeValue.contains(FIELD_SEPARATOR))
                        .orElseGet(() -> httpStatus.value() + FIELD_SEPARATOR + errorCode.value());

        final String sanitizedMessage = environment.getProperty(code, message);

        final ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .timestamp(ZonedDateTime.now().withZoneSameInstant(ZoneOffset.UTC))
                .name(httpStatus.getReasonPhrase())
                .description(sanitizedMessage)
                .status(httpStatus.value())
                .code(code)
                .resource(httpServletRequest.getRequestURI())
                .build();

        return new ResponseEntity<>(apiErrorResponse, httpStatus);
    }

    @Builder
    @NonNull
    @lombok.Value
    public static class ApiErrorResponse {
        private static final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss[.SSS]X";

        String name;
        Integer status;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN)
        ZonedDateTime timestamp;
        String code;
        String resource;
        String description;
        Map<String, String> metadata;
    }

}
