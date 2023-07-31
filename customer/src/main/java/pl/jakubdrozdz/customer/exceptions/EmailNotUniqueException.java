package pl.jakubdrozdz.customer.exceptions;

public class EmailNotUniqueException extends RuntimeException {
    public EmailNotUniqueException(String message) {
        super(message);
    }

    public EmailNotUniqueException(String message, Throwable errorCause) {
        super(message, errorCause);
    }
}
