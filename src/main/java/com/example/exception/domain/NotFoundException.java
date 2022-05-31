package com.example.exception.domain;

public class NotFoundException extends RuntimeException {
    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(String entity, String byWhat) {
        super(String.format("%s not found by %s", entity, byWhat));
    }

    public NotFoundException(String entity, String byWhat, Throwable cause) {
        super(String.format("%s not found by %s", entity, byWhat), cause);
    }
}
