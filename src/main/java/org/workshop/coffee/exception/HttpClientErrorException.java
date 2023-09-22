package org.workshop.coffee.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class HttpClientErrorException extends RuntimeException {
    public HttpClientErrorException(String message) {
        super(message);
    }

    public HttpClientErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
