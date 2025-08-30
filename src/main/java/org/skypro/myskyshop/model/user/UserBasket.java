package org.skypro.myskyshop.model.user;

import java.util.List;

public record UserBasket(List<BasketItem> items, double total) {
}
