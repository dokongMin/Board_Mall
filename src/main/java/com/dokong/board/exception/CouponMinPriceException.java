package com.dokong.board.exception;

public class CouponMinPriceException extends RuntimeException{

    public CouponMinPriceException() {
        super();
    }

    public CouponMinPriceException(String message) {
        super(message);
    }

    public CouponMinPriceException(String message, Throwable cause) {
        super(message, cause);
    }

    public CouponMinPriceException(Throwable cause) {
        super(cause);
    }

    protected CouponMinPriceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
