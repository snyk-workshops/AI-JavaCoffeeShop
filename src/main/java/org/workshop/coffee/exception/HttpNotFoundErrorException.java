package org.workshop.coffee.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class HttpNotFoundErrorException extends HttpClientErrorException {
    public HttpNotFoundErrorException(String message) {
        super(message);
    }

    public HttpNotFoundErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
