package com.ddabadi.util;

public class SmStockException extends RuntimeException {

    public SmStockException() {
        super();
    }

    public SmStockException(String message) {
        super(message);
    }

    public SmStockException(String message, Throwable cause) {
        super(message, cause);
    }

    public SmStockException(Throwable cause) {
        super(cause);
    }

    protected SmStockException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
