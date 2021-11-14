package com.smartdubai.exceptions;

public class InvalidBookException extends Exception{
    public InvalidBookException() {
    }

    public InvalidBookException(String message) {
        super(message);
    }

    public InvalidBookException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidBookException(Throwable cause) {
        super(cause);
    }
}
