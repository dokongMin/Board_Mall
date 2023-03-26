package com.dokong.board.exception;

public class FiveBoardPostPerDay extends RuntimeException{

    public FiveBoardPostPerDay() {
        super();
    }

    public FiveBoardPostPerDay(String message) {
        super(message);
    }

    public FiveBoardPostPerDay(String message, Throwable cause) {
        super(message, cause);
    }

    public FiveBoardPostPerDay(Throwable cause) {
        super(cause);
    }

    protected FiveBoardPostPerDay(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
