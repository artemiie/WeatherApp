package org.weatherapp.exception;

import javax.servlet.ServletException;

public class UserNotLoggedException extends ServletException {
    public UserNotLoggedException(String message) {
        super(message);
    }

    public UserNotLoggedException(String message, Throwable rootCause) {
        super(message, rootCause);
    }

}
