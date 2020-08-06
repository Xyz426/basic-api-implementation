package com.thoughtworks.rslist.exception;

public class InvalidPostUserParamException extends RuntimeException {
    private String message;

    public InvalidPostUserParamException(String message) {
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
