package org.skypro.myskyshop.exceptions;

import java.util.UUID;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(UUID productId) {
        super("Товар с идентификатором " + productId + " не найден");
    }
}