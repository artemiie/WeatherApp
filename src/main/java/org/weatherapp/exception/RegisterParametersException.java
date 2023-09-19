package org.weatherapp.exception;

import javax.servlet.ServletException;

public class RegisterParametersException extends ServletException {
    public RegisterParametersException(String message) {
        super(message);
    }

    public RegisterParametersException(String message, Exception e) {
        super(message, e);
    }
}
