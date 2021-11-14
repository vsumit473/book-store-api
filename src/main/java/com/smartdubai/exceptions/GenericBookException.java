package com.smartdubai.exceptions;

public class GenericBookException extends Exception{

    public GenericBookException() {
    }

    public GenericBookException(String message) {
        super(message);
    }

    public GenericBookException(String message, Throwable cause) {
        super(message, cause);
    }

    public GenericBookException(Throwable cause) {
        super(cause);
    }
}
