package org.weatherapp.exception;

import javax.servlet.ServletException;

public class LoginParametersException extends ServletException {
    public LoginParametersException(String message) {
        super(message);
    }

    public LoginParametersException(String message, Exception e) {
        super(message, e);
    }
}
