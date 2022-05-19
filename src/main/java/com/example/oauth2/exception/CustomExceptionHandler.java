package com.example.oauth2.exception;

import com.example.oauth2.exception.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @ExceptionHandler({NotFoundException.class, UsernameNotFoundException.class})
    public ResponseEntity<HttpResponseException> notFoundException(NotFoundException e) {
        LOGGER.debug(e.getMessage());
        return createHttpResponse(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<HttpResponseException> badRequestException(BadRequestException e) {
        LOGGER.debug(e.getMessage());
        return createHttpResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<HttpResponseException> internalServerException(InternalServerException e) {
        LOGGER.debug(e.getMessage());
        return createHttpResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<HttpResponseException> unauthorizedException(UnauthorizedException e) {
        LOGGER.debug(e.getMessage());
        return createHttpResponse(HttpStatus.UNAUTHORIZED, e.getMessage());
    }

    private ResponseEntity<HttpResponseException> createHttpResponse(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponseException(httpStatus.value(), httpStatus,
            httpStatus.getReasonPhrase().toUpperCase(), message), httpStatus);
    }
}