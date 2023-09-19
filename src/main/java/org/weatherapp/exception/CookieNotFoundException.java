package org.weatherapp.exception;

import javax.servlet.ServletException;

public class CookieNotFoundException extends ServletException {
    public CookieNotFoundException(String message) {
        super(message);
    }

    public CookieNotFoundException(String message, Throwable rootCause) {
        super(message, rootCause);
    }

}
