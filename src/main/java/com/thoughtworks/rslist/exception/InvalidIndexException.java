package com.thoughtworks.rslist.exception;

public class InvalidIndexException extends RuntimeException {
    String message;

    public InvalidIndexException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
