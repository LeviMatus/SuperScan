package main.com.superscan.dfa;

public class InvalidTokenException extends Exception {
    public InvalidTokenException(String errorMessage) {
        super(errorMessage);
    }
}
