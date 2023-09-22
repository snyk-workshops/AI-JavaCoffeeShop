package org.workshop.coffee.exception;

public class EmailTakenException extends Exception {
    public EmailTakenException(String message) {
        super(message);
    }

    public EmailTakenException(String message, Throwable cause) {
        super(message, cause);
    }
}
