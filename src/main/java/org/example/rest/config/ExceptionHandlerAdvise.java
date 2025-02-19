package org.example.rest.config;

import lombok.extern.slf4j.Slf4j;
import org.example.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlerAdvise {

    @ExceptionHandler({MissingRequestValueException.class,
            MethodArgumentNotValidException.class})
    public ResponseEntity<String> handleBadRequestException(
            Exception exception, HttpServletRequest request) {

        String requestBody = getRequestBody(request);
        log.error("[ExceptionHandlerAdvise][handleNoSuchElementException] error: {}, Path: {}, Params: {}, Body: {}",
                exception.getMessage(),
                request.getRequestURL(),
                request.getQueryString(),
                requestBody);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Void> handleNoSuchElementException(
            NoSuchElementException noSuchElementException, HttpServletRequest request) {

        String requestBody = getRequestBody(request);
        log.error("[ExceptionHandlerAdvise][handleNoSuchElementException] error: {}, Path: {}, Params: {}, Body: {}",
                noSuchElementException.getMessage(),
                request.getRequestURL(),
                request.getQueryString(),
                requestBody);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorCode> handleException(Exception exception, HttpServletRequest request) {

        String requestBody = getRequestBody(request);
        log.error("[ExceptionHandlerAdvise][handleException] error: {}, Path: {}, Params: {}, Body: {}",
                exception.getMessage(),
                request.getRequestURL(),
                request.getQueryString(),
                requestBody);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorCode.SYSTEM_ERROR);
    }

    private String getRequestBody(HttpServletRequest request) {
        ContentCachingRequestWrapper cachingRequest = (request instanceof ContentCachingRequestWrapper)
                ? (ContentCachingRequestWrapper) request
                : new ContentCachingRequestWrapper(request);

        byte[] content = cachingRequest.getContentAsByteArray();
        return (content.length > 0) ? new String(content, StandardCharsets.UTF_8) : "[empty]";
    }
}
