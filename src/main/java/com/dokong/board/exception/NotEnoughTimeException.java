package com.dokong.board.exception;

public class NotEnoughTimeException extends RuntimeException{

    public NotEnoughTimeException() {
        super();
    }

    public NotEnoughTimeException(String message) {
        super(message);
    }

    public NotEnoughTimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughTimeException(Throwable cause) {
        super(cause);
    }

    protected NotEnoughTimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
