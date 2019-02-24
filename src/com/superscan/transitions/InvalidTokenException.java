package com.superscan.transitions;

public class InvalidTokenException extends Exception {
    public InvalidTokenException(String errorMessage) {
        super(errorMessage);
    }
}
