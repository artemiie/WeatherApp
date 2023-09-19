package org.weatherapp.exception;

import javax.servlet.ServletException;

public class UserNotFoundException extends ServletException {
    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable rootCause) {
        super(message, rootCause);
    }

}
