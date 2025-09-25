package org.skypro.myskyshop.exceptions;

public class InvalidSearchPatternException extends RuntimeException {

    public InvalidSearchPatternException() {
        super("Поисковый запрос не может быть пустым или состоять из пробелов!");
    }
}
