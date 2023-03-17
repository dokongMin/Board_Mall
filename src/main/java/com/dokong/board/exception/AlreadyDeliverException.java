package com.dokong.board.exception;

public class AlreadyDeliverException extends RuntimeException{

    public AlreadyDeliverException() {
        super();
    }

    public AlreadyDeliverException(String message) {
        super(message);
    }

    public AlreadyDeliverException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadyDeliverException(Throwable cause) {
        super(cause);
    }

    protected AlreadyDeliverException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
