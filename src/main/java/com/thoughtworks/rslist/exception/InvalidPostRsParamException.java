package com.thoughtworks.rslist.exception;

public class InvalidPostRsParamException extends RuntimeException {
    private String message;

    public InvalidPostRsParamException(String message) {
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
