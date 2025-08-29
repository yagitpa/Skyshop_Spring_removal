package org.skypro.myskyshop.exceptions;

public class BasketFormationException extends RuntimeException{

    public BasketFormationException(String message) {
        super(message);
    }

    public BasketFormationException(String message, Throwable cause) {
        super(message, cause);
    }
}
