package pl.jakubdrozdz.customer.exceptions;

public class UserNotValidatedException extends RuntimeException {
    public UserNotValidatedException(String message) {
        super(message);
    }

    public UserNotValidatedException(String message, Throwable errorCause) {
        super(message, errorCause);
    }
}
