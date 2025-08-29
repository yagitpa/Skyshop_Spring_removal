package org.skypro.myskyshop.exceptions;

public class InvalidArticleException extends RuntimeException {

    public InvalidArticleException(String message) {
        super(message);
    }

    public InvalidArticleException(String message, Throwable cause) {
        super(message, cause);
    }
}
