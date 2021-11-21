package org.playwing.game.core.commons.exception;

public class RequestHandlerNotFoundException extends RuntimeException {

    public RequestHandlerNotFoundException() {
        super();
    }

    public RequestHandlerNotFoundException(String message) {
        super(message);
    }

    public RequestHandlerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestHandlerNotFoundException(Throwable cause) {
        super(cause);
    }

    protected RequestHandlerNotFoundException(String message, Throwable cause,
                                            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
