package org.luisangel.msvcoperation.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends Exception {

    private int httpStatusCode;

    public CustomException(int httpStatusCode, String message) {
        super(message);
        this.httpStatusCode = httpStatusCode;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }
}
