package com.example.securityapplication.Exception;

import org.springframework.expression.spel.CompiledExpression;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
