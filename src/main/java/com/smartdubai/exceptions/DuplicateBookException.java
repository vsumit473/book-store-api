package com.smartdubai.exceptions;

public class DuplicateBookException extends Exception{
    public DuplicateBookException() {
    }

    public DuplicateBookException(String message) {
        super(message);
    }

    public DuplicateBookException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateBookException(Throwable cause) {
        super(cause);
    }
}
