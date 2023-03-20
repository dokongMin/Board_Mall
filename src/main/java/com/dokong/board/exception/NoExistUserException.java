package com.dokong.board.exception;

public class NoExistUserException extends RuntimeException{

    public NoExistUserException() {
        super();
    }

    public NoExistUserException(String message) {
        super(message);
    }

    public NoExistUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoExistUserException(Throwable cause) {
        super(cause);
    }

    protected NoExistUserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
