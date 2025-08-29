package org.skypro.myskyshop.model.user;

import org.skypro.myskyshop.model.product.Product;

public record BasketItem(Product product, int quantity) {
}
