package org.weatherapp.exception;

import javax.servlet.ServletException;

public class SearchParametersException extends ServletException {
    public SearchParametersException(String message) {
        super(message);
    }

    public SearchParametersException(String message, Exception e) {
        super(message, e);
    }
}
