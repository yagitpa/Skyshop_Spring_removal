package org.skypro.myskyshop.model.user;

import java.util.List;

public class UserBasket {
    private final List<BasketItem> items;
    private final double total;

    public UserBasket(List<BasketItem> items) {
        this.items = items;
        this.total = calculatedTotal(items);
    }

    public List<BasketItem> getItems() {
        return items;
    }

    public double getTotal() {
        return total;
    }

    private double calculatedTotal(List<BasketItem> items) {
        return items.stream()
                .mapToDouble(item -> item.quantity() * item.product().getPrice())
                .sum();
    }
}
