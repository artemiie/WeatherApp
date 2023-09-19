package org.weatherapp.exception;

import javax.servlet.ServletException;

public class NotValidLocationParamatersException extends ServletException {
    public NotValidLocationParamatersException(String message) {
        super(message);
    }

    public NotValidLocationParamatersException(String message, Throwable rootCause) {
        super(message, rootCause);
    }

}
